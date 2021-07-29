package com.example.muzee.seller.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.databinding.FragmentSellerOrderDetailBinding

class SellerOrderDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentSellerOrderDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val order = SellerOrderDetailFragmentArgs.fromBundle(requireArguments()).selectedOrder
        val viewModelFactory = SellerOrderDetailViewModelFactory(order, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(SellerOrderDetailViewModel::class.java)
        return binding.root
    }
}