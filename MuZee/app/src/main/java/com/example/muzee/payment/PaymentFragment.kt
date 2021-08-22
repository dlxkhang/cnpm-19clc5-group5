package com.example.muzee.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.muzee.cart.CartViewModel
import com.example.muzee.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private val sharedViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPaymentBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding.viewModel =  sharedViewModel

        // Sets the adapter of the RecyclerView
        binding.recyclerView.adapter = PaymentAdapter()

        return binding.root
    }

}