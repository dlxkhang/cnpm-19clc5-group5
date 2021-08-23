package com.example.muzee.seller.product_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerProductOverviewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SellerProductOverviewFragment : Fragment() {

    private val viewModel: SellerProductOverviewViewModel by viewModels()
    lateinit var binding: FragmentSellerProductOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSellerProductOverviewBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        // Sets the adapter of the photosGrid RecyclerView
        binding.recyclerView.adapter = SellerProductOverviewAdapter(SellerProductOverviewAdapter.OnClickListener {
            viewModel.displayProductDetail(it)
        })
        val SID = SellerProductOverviewFragmentArgs.fromBundle(requireArguments()).sellerID
        var sellerID = ""
        SID?.let{
            sellerID = it
        }
        viewModel.getProducts(sellerID)
        val add_btn = binding.addNewProductBtn
        add_btn.setOnClickListener{
            findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToAddNewProductFragment(SID))
        }
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                SellerProductOverviewViewModel.ApiStatus.DONE->{

                }
                SellerProductOverviewViewModel.ApiStatus.ERROR->{
                    showSnackBar()
                }
            }
        })
        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToSellerProductDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
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