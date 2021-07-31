package com.example.muzee.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()

    private var binding: FragmentCartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCartBinding.inflate(inflater, container, false)

        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        fragmentBinding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        fragmentBinding.viewModel = viewModel

        // Sets the adapter of the RecyclerView
        fragmentBinding.recyclerView.adapter = CartAdapter()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.cartFragment = this
    }

    fun goToPayment() {
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentFragment())
    }
}