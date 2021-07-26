package com.example.muzee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.databinding.FragmentCategoryBinding
import com.example.muzee.model.CategoryViewModel


class CategoryFragment : Fragment(){
    private var binding:FragmentCategoryBinding? = null
    private val sharedViewModel:CategoryViewModel by activityViewModels()

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
        binding?.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // assign view model to a property in binding class
            viewModel = sharedViewModel

            // assign fragment
            categoryFragment = this@CategoryFragment
        }
    }
    fun sendRequestandBack(pickedCategory: String){
        sharedViewModel.updateCategoryKey(pickedCategory)
        findNavController().navigate(R.id.action_categoryFragment_to_mainScreenNormalUsersFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}