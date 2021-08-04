package com.example.muzee.oldProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muzee.R
import com.example.muzee.databinding.FragmentEditOldProductBinding

class EditOldProductFragment : Fragment() {
    var binding:FragmentEditOldProductBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentbinding = FragmentEditOldProductBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        return fragmentbinding.root
    }
}