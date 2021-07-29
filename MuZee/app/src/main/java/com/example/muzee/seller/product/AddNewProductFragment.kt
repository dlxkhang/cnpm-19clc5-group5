package com.example.muzee.seller.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.muzee.databinding.FragmentAddNewProductBinding


class AddNewProductFragment : Fragment() {

    private var binding: FragmentAddNewProductBinding? = null // binding fragment_add_new_product.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("Add New Product") // set title text for seller product screen

        return fragmentBinding.root
    }
}