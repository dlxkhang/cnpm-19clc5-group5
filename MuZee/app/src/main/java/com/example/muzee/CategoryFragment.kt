package com.example.muzee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muzee.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment(){
    private var binding:FragmentCategoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentbinding = FragmentCategoryBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        return fragmentbinding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.categoryFragment = this
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}