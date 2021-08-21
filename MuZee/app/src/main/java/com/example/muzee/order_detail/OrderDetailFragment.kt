package com.example.muzee.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUserOrder
import com.example.muzee.databinding.FragmentOrderDetailBinding

class OrderDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentOrderDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.recyclerView.adapter = OrderDetailAdapter()

        val order = OrderDetailFragmentArgs.fromBundle(requireArguments()).selectedOrder
        val viewModelFactory = OrderDetailViewModelFactory(order as NormalUserOrder, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(OrderDetailViewModel::class.java)
        return binding.root
    }
}