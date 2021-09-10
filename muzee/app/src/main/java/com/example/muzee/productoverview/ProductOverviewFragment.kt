package com.example.muzee.productoverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.muzee.databinding.FragmentProductOverviewBinding

class ProductOverviewFragment : Fragment() {

    private val viewModel: ProductOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the  RecyclerView
        binding.recyclerView.adapter = ProductOverviewAdapter(viewModel, ProductOverviewAdapter.OnClickListener {
            viewModel.displayProductDetail(it)
        })

        return binding.root
    }
}