package com.example.muzee.seller.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.databinding.FragmentSellerProductDetailBinding

class SellerProductDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentSellerProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = SellerProductDetailFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = SellerProductDetailViewModelFactory(product, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(SellerProductDetailViewModel::class.java)

//        val activity = activity as AppCompatActivity? // get activity
//        activity!!.supportActionBar?.setTitle("Product Detail") // set title text for seller product detail screen
        return binding.root
    }
}