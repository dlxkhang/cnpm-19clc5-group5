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
import com.example.muzee.databinding.FragmentSellerProductBinding


class SellerProductFragment : Fragment() {

    private var binding: FragmentSellerProductBinding? = null // binding fragment_seller_product.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentSellerProductBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("My Product") // set title text for seller product screen

        return fragmentBinding.root
    }
}