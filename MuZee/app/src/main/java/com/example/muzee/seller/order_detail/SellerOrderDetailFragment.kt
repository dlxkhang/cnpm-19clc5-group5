package com.example.muzee.seller.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerOrderDetailBinding
import com.example.muzee.network.seller.order.Order_responseItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SellerOrderDetailFragment : Fragment() {
    private val viewModel:SellerOrderDetailViewModel by lazy{
        ViewModelProvider(this).get(SellerOrderDetailViewModel::class.java)
    }
    private var binding:FragmentSellerOrderDetailBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentSellerOrderDetailBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding?.lifecycleOwner = this

        binding?.recyclerView?.adapter = SellerOrderDetailAdapter()

        val order = SellerOrderDetailFragmentArgs.fromBundle(requireArguments()).selectedOrder
        val viewModelFactory = SellerOrderDetailViewModelFactory(order as Order_responseItem, application)
        binding?.viewModel = ViewModelProvider(
            this, viewModelFactory).get(SellerOrderDetailViewModel::class.java)
        val SID = SellerOrderDetailFragmentArgs.fromBundle(requireArguments()).sellerID
        var sellerID = ""
        SID?.let{
            sellerID = it
        }
        binding?.acceptButton?.setOnClickListener{
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Accept the order?")
                .setMessage("Do you sure to accept the order? ")
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.cancel()
                    viewModel.acceptOrder(sellerID)
                }
                .setNegativeButton("No"){
                        dialog,which-> dialog.cancel()
                }
            dialog.show()
        }
        binding?.cancelButton?.setOnClickListener{
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Cancel the order?")
                .setMessage("Do you sure to cancel the order? ")
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.cancel()
                    viewModel.cancelOrder(sellerID)
                }
                .setNegativeButton("No"){
                        dialog,which-> dialog.cancel()
                }
            dialog.show()
        }
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                SellerOrderDetailViewModel.ApiStatus.SUCCESS->{
                    handleSuccessCase()
                }
                SellerOrderDetailViewModel.ApiStatus.ERROR->{
                    showSnackBar()
                }
            }
        })


        return fragmentbinding.root
    }
    private fun handleSuccessCase(){
        viewModel.ack_accept.observe(viewLifecycleOwner,{
            when(it.ack){
                "order_seller_already_accepted"->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("This order has been accepted before")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.cancel()
                        }
                    dialog.show()
                }
                "cannot_accept_an_canceled_order"->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("This order has been canceled before")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.cancel()
                        }
                    dialog.show()
                }
                "accept_order_seller_success"->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("Accept order successful!!!")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.cancel()
                        }
                    dialog.show()
                }
            }
        })
        viewModel.ack_cancel.observe(viewLifecycleOwner,{
            when(it.ack){
                "order_seller_already_canceled"->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("This order has been canceled before")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.cancel()
                        }
                    dialog.show()
                }
                "cancel_order_seller_success"->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("Cancel order successful!!!")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.cancel()
                        }
                    dialog.show()
                }
            }
        })
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root, R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
    }
}