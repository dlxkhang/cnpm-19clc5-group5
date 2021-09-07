package com.example.muzee.oldProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.databinding.FragmentOldProductStoreBinding
import com.example.muzee.order_overview.OrderOverviewFragmentDirections

class oldProductStoreFragment : Fragment() {
    private var binding : FragmentOldProductStoreBinding? = null

    private val args: oldProductStoreFragmentArgs by navArgs()

    private lateinit var viewModel: OldProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentOldProductStoreBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding?.lifecycleOwner = this
        val viewModelFactory = OldProductViewModelFactory(args.NID!!, requireNotNull(activity).application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OldProductViewModel::class.java)
        // Giving the binding access to the oldProductViewModel
        viewModel.getOldProducts()
        binding?.viewModel = viewModel
        binding?.recycleViewOldProduct?.adapter = OldProductAdapter(OldProductAdapter.OnClickListener{
            viewModel.displayOldProductDetail(it)
        }, viewModel)

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(oldProductStoreFragmentDirections.actionOldProductStoreFragmentToEditOldProductFragment(args.NID,it))
                viewModel.displayOldProductDetailComplete()
            }
        })

        binding?.btnPost?.setOnClickListener {
            this.findNavController().navigate(oldProductStoreFragmentDirections.actionOldProductStoreFragmentToAddOldProductFragment(args.NID))
        }
        return fragmentBinding.root
    }
}