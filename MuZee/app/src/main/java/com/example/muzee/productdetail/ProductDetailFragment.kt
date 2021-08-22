package com.example.muzee.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.databinding.FragmentProductDetailBinding
import com.example.muzee.login.LoginViewModel

class ProductDetailFragment : Fragment() {

    private val sharedViewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentProductDetailBinding? = null

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = ProductDetailFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = ProductDetailViewModelFactory(product, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(ProductDetailViewModel::class.java)

        _binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@ProductDetailFragment
        }
    }

    fun addToCart() {
        _binding?.viewModel?.addProductToCart(args.NID)
        Toast.makeText(context, "Add successfully", Toast.LENGTH_SHORT)
        findNavController().navigate(R.id.action_productDetailFragment_to_mainScreenNormalUsersFragment)
    }
}