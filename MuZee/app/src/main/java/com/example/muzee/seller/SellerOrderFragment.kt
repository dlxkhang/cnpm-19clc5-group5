package com.example.muzee.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentMainScreenSellerBinding
import com.example.muzee.databinding.FragmentSellerOrderBinding
import com.example.muzee.databinding.FragmentSellerProductBinding


class SellerOrderFragment : Fragment() {

    private var binding: FragmentSellerOrderBinding? = null // binding fragment_seller_order.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentSellerOrderBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("Order List") // set title text for seller order screen

        return fragmentBinding.root
    }
}