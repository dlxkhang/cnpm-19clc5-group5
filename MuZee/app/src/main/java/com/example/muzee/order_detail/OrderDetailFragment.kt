package com.example.muzee.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.databinding.FragmentOrderDetailBinding

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null

    private val args: OrderDetailFragmentArgs by navArgs()

    private val viewModel: OrderDetailViewModel by activityViewModels { OrderDetailViewModelFactory(OrderDetailFragmentArgs.fromBundle(requireArguments()).selectedOrder, args.NID, requireNotNull(activity).application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentOrderDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.recyclerView.adapter = OrderDetailAdapter()

        viewModel._selectedOrder.value = OrderDetailFragmentArgs.fromBundle(requireArguments()).selectedOrder
        binding.viewModel = viewModel
        _binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@OrderDetailFragment
        }
    }

    fun cancelOrder() {
        viewModel.cancelOrder()
        findNavController().popBackStack()
    }

}