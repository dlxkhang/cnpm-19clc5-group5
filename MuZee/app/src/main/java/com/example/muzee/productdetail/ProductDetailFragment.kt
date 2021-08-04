package com.example.muzee.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = ProductDetailFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = ProductDetailViewModelFactory(product, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(ProductDetailViewModel::class.java)
        return binding.root
    }
}