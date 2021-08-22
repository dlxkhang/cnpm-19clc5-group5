package com.example.muzee.seller.order_overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerOrderBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SellerOrderFragment : Fragment() {

    private val viewModel: SellerOrderViewModel by viewModels()
    private var binding: FragmentSellerOrderBinding? = null // binding fragment_seller_order.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup data binding
        val fragmentBinding = FragmentSellerOrderBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding!!.lifecycleOwner = this

        // Giving the binding access to the OrderViewModel
        binding!!.orderViewModel = viewModel


        binding!!.recyclerView.adapter = SellerOrderAdapter(SellerOrderAdapter.OnClickListener {
            viewModel.displayOrderDetail(it)
        })
        val SID = SellerOrderFragmentArgs.fromBundle(requireArguments()).sellerID
        viewModel.getListOfOrders(SID)
        viewModel.status.observe(viewLifecycleOwner,{
            when(it)
            {
                SellerOrderViewModel.ApiStatus.DONE->{

                }
                SellerOrderViewModel.ApiStatus.ERROR->{
                    showSnackBar()
                }
                SellerOrderViewModel.ApiStatus.LOADING->{

                }
            }
        })
        viewModel.navigateToSelectedOrder.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(SellerOrderFragmentDirections.actionSellerOrderFragmentToSellerOrderDetailFragment22(it,SID))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return fragmentBinding.root
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root, R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
    }
}
