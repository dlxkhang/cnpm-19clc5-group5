package com.example.muzee.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var binding: FragmentCartBinding? = null

    private val args: CartFragmentArgs by navArgs()

    private val sharedViewModel: CartViewModel by activityViewModels { CartViewModelFactory(args.NID, requireNotNull(activity).application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCartBinding.inflate(inflater, container, false)

        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        fragmentBinding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        fragmentBinding.viewModel = sharedViewModel
        // Sets the adapter of the RecyclerView
        fragmentBinding.recyclerView.adapter = CartAdapter(sharedViewModel)

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