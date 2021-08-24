package com.example.muzee.seller.product_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerProductOverviewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SellerProductOverviewFragment : Fragment() {

    private lateinit var viewmodel: SellerProductOverviewViewModel
    lateinit var binding: FragmentSellerProductOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding = FragmentSellerProductOverviewBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val SID = SellerProductOverviewFragmentArgs.fromBundle(requireArguments()).sellerID
        val sellerInfo = SellerProductOverviewFragmentArgs.fromBundle(requireArguments()).sellerInfo
        var sellerID = ""
        SID?.let{
            sellerID = it
        }
        val viewModelFactory = SellerProductOverviewViewModelFactory(sellerID,application)
        viewmodel = ViewModelProvider(this,viewModelFactory).get(SellerProductOverviewViewModel::class.java)
        // Sets the adapter of the photosGrid RecyclerView
        binding.viewModel = viewmodel
        binding.recyclerView.adapter = SellerProductOverviewAdapter(SellerProductOverviewAdapter.OnClickListener {
            viewmodel.displayProductDetail(it)
        },viewmodel,this,SID)

        val add_btn = binding.addNewProductBtn
        add_btn.setOnClickListener{
            findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToAddNewProductFragment(SID))
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
        viewmodel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToSellerProductDetailFragment(it,sellerInfo!!))
                viewmodel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
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
        Snackbar.make(binding.root,R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
    }

}