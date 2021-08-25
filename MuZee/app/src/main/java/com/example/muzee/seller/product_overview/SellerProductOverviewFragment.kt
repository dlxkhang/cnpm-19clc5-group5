package com.example.muzee.seller.product_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerProductOverviewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SellerProductOverviewFragment : Fragment() {

    private val viewmodel: SellerProductOverviewViewModel by activityViewModels{SellerProductOverviewViewModelFactory(args.sellerID!!,
        requireNotNull(activity).application)}
    private var binding: FragmentSellerProductOverviewBinding? = null
    val args:SellerProductOverviewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmmentbinding = FragmentSellerProductOverviewBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding = fragmmentbinding
        binding?.lifecycleOwner = this

        val SID = args.sellerID
        val sellerInfo = args.sellerInfo
        var sellerID = ""
        SID?.let{
            sellerID = it
        }
        viewmodel.getNewProducts()
        // Sets the adapter of the photosGrid RecyclerView
        binding?.viewModel = viewmodel
        binding?.recyclerView?.adapter = SellerProductOverviewAdapter(SellerProductOverviewAdapter.OnClickListener {
            viewmodel.displayProductDetail(it)
        },viewmodel,this,SID)

        val add_btn = binding?.addNewProductBtn
        add_btn?.setOnClickListener{
            findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToAddNewProductFragment(SID,args.sellerInfo))
        }
        viewmodel.status.observe(viewLifecycleOwner,{
            when(it){
                SellerProductOverviewViewModel.ApiStatus.DONE->{

                }
                SellerProductOverviewViewModel.ApiStatus.ERROR->{
                    showSnackBar()
                }
            }
        })
        viewmodel.status_del.observe(viewLifecycleOwner,{
            when(it){
                SellerProductOverviewViewModel.ApiStatus_Delete.ERROR->{
                    showSnackBar()
                }
                SellerProductOverviewViewModel.ApiStatus_Delete.SUCCESS->{
                    showDiaLog("Delete product successful","This product is removed in the system")
                    viewmodel.getNewProducts()
                }
                SellerProductOverviewViewModel.ApiStatus_Delete.NOTEXIST->{
                    showDiaLog("Delete product failed","This product is not existed in system")
                }
            }
        })
        viewmodel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToSellerProductDetailFragment(it,sellerInfo!!,args.sellerID))
                viewmodel.displayPropertyDetailsComplete()
            }
        })

        return fragmmentbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            sellerProductOverviewFragment = this@SellerProductOverviewFragment
        }
    }
    private fun showDiaLog(title:String,message: String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root,R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
    }

}