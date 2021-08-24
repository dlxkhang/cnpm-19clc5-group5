package com.example.muzee.oldProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.databinding.FragmentOldProductStoreBinding

class oldProductStoreFragment : Fragment() {
    private var binding : FragmentOldProductStoreBinding? = null

    private val args: oldProductStoreFragmentArgs by navArgs()

    private val viewModel: OldProductViewModel by activityViewModels { OldProductViewModelFactory(args.NID, requireNotNull(activity).application) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentOldProductStoreBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding?.lifecycleOwner = this
        // Giving the binding access to the oldProductViewModel
        binding?.viewModel = viewModel
        binding?.recycleViewOldProduct?.adapter = OldProductAdapter(OldProductAdapter.OnClickListener{
            viewModel.displayOldProductDetail(it)
        }, viewModel)
        binding?.btnPost?.setOnClickListener {
            findNavController().navigate(R.id.action_oldProductStoreFragment_to_addOldProductFragment)
        }
        return fragmentBinding.root
    }
}