package com.example.muzee.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by activityViewModels()

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

    fun addToCart() {
        // viewModel.addProductToCart()
        findNavController().navigate(R.id.action_productDetailFragment_to_mainScreenNormalUsersFragment)
    }
}