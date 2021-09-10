package com.example.muzee.oldproduct_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.muzee.databinding.FragmentOldProductDetailBinding

class OldProductDetailFragment : Fragment() {
    private var binding:FragmentOldProductDetailBinding? = null
    private val args:OldProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentOldProductDetailBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding?.lifecycleOwner = this
        val oldproduct = args.selectedOldProduct
        val viewModelFactory = OldProductDetailViewModelFactory(oldproduct,application)
        binding?.viewModel = ViewModelProvider(this,viewModelFactory
        ).get(OldProductDetailViewModel::class.java)
        return fragmentbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@OldProductDetailFragment
        }
    }
}