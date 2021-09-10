package com.example.muzee.payment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
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
        if (sharedViewModel.address.value == "") {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage("Please enter your address")
            builder.setPositiveButton("OK") { dialog, id ->
                print(0)
            }
            var alert: AlertDialog = builder.create()
            alert.show()
        }
        else {
            sharedViewModel.placeOrder()
            sharedViewModel.getProducts(sharedViewModel.NID)
            Toast.makeText(context, "Place order successfully", Toast.LENGTH_LONG).show()
            findNavController().popBackStack(R.id.mainScreenNormalUsersFragment, false)
        }

    }

    fun enterAddress() {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("Address")
        builder.setMessage("Please enter your address")
        val input = EditText(context)
        builder.setView(input)
        builder.setNegativeButton("Cancel") { dialog, id ->
            print(0)
        }
        builder.setPositiveButton("Submit") { dialog, id ->
            sharedViewModel.address.value = input.text.toString()
        }
        var alert: AlertDialog = builder.create()
        alert.show()
    }
}