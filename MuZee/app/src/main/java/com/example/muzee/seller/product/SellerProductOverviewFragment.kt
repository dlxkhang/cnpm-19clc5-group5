package com.example.muzee.seller.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSellerProductOverviewBinding


class SellerProductOverviewFragment : Fragment() {

    private val viewModel: SellerProductOverviewViewModel by viewModels()
    lateinit var binding: FragmentSellerProductOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("My Product") // set title text for seller product screen

        binding = FragmentSellerProductOverviewBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        // Sets the adapter of the photosGrid RecyclerView
        binding.recyclerView.adapter = SellerProductOverviewAdapter()

        return binding.root
    }

    fun goToAddNewProductScreen() {
        findNavController().navigate(R.id.action_sellerProductOverviewFragment_to_addNewProductFragment)
    }
}