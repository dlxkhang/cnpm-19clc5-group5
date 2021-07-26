package com.example.muzee.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.muzee.databinding.FragmentSellerOrderBinding


class SellerOrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels()
    private var binding: FragmentSellerOrderBinding? = null // binding fragment_seller_order.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentSellerOrderBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding!!.lifecycleOwner = this

        // Giving the binding access to the OrderViewModel
        binding!!.orderViewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding!!.recyclerView.adapter = OrderAdapter()


        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("Order List") // set title text for seller order screen

        return fragmentBinding.root
    }
}
