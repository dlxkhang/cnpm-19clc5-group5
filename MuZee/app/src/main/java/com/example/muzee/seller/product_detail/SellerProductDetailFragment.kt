package com.example.muzee.seller.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.databinding.FragmentSellerProductDetailBinding

class SellerProductDetailFragment : Fragment() {
    private var _binding:FragmentSellerProductDetailBinding? = null
    private val args:SellerProductDetailFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentSellerProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = args.selectedProduct
        val seller = args.sellerInfo
        val viewModelFactory = SellerProductDetailViewModelFactory(seller,product,application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(SellerProductDetailViewModel::class.java)
//        val activity = activity as AppCompatActivity? // get activity
//        activity!!.supportActionBar?.setTitle("Product Detail") // set title text for seller product detail screen
        binding.editButton.setOnClickListener {
            findNavController().navigate(SellerProductDetailFragmentDirections.actionSellerProductDetailFragmentToEditNewProductFragment(product,args.sellerID,seller))
        }
        _binding = binding
        return binding.root
    }

}