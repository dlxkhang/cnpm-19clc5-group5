package com.example.muzee.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.cart.CartViewModel
import com.example.muzee.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private var binding: FragmentPaymentBinding? = null

    private val sharedViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPaymentBinding.inflate(inflater)

        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        fragmentBinding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        fragmentBinding.viewModel =  sharedViewModel

        // Sets the adapter of the RecyclerView
        fragmentBinding.recyclerView.adapter = PaymentAdapter()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.paymentFragment = this
    }

    fun placeOrder() {
        sharedViewModel.placeOrder("102 Trung Hoa")
        sharedViewModel.getProducts(sharedViewModel.NID)
        findNavController().popBackStack(R.id.mainScreenNormalUsersFragment, false)
    }

}