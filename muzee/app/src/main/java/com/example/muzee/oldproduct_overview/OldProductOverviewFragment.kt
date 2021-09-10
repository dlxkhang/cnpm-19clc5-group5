package com.example.muzee.oldproduct_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.databinding.FragmentOldProductOverviewBinding

class OldProductOverviewFragment : Fragment() {
    private val viewModel: OldProductOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOldProductOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        // Sets the adapter of the  RecyclerView
        binding.recyclerView.adapter = OldProductOverviewAdapter(viewModel, OldProductOverviewAdapter.OnClickListener {
            viewModel.displayOldProductDetail(it)
        })

        viewModel.navigateToSelectedOldProduct.observe(viewLifecycleOwner,{
            if(null!=it){
                this.findNavController().navigate(OldProductOverviewFragmentDirections.actionOldProductOverviewFragmentToOldProductDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })



        return binding.root
    }
}