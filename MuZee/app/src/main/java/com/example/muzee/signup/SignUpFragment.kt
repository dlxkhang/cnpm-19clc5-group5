package com.example.muzee.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.muzee.R
import com.example.muzee.databinding.SignUpFragmentBinding
import com.google.android.material.tabs.TabLayout

class SignUpFragment : Fragment() {

    private lateinit var viewmodel: SignUpViewModel
    private var binding: SignUpFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentbinding = SignUpFragmentBinding.inflate(inflater,container,false)
        binding = fragmentbinding

        return fragmentbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner
        }
        var tab_layout:TabLayout? = binding?.tabLayout
        var view_page = initViewPager()
        tab_layout!!.setupWithViewPager(view_page)
    }
    override fun onDestroyView(){
        super.onDestroyView()
        binding = null
    }
    private fun initViewPager(): ViewPager? {
        var view_page = binding?.viewPager
        // init the adapter
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity().supportFragmentManager)
        // init the fragments
        pagerAdapter.addFragment(getString(R.string.tab_normal_users))
        pagerAdapter.addFragment( getString(R.string.tab_seller))
        // set adapter to ViewPager
        view_page?.adapter = pagerAdapter
        return view_page
    }

}