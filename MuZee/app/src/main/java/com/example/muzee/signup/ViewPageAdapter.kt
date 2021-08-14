package com.example.muzee.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ScreenSlidePagerAdapter(fm:FragmentManager): FragmentStatePagerAdapter(fm){
    private val tabTitleList = ArrayList<String>()
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment = when (position){
            0-> SignUp_NormalUsersFragment()
            1 ->  SignUp_SellerFragment()
            else -> SignUp_NormalUsersFragment()
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList.get(position)
    }
    fun addFragment(title: String) {
        tabTitleList.add(title)
    }
}