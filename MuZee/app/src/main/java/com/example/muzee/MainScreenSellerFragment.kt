package com.example.muzee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.muzee.databinding.FragmentMainScreenSellerBinding
import com.google.android.material.navigation.NavigationView


class MainScreenSellerFragment : Fragment() {

    lateinit var toggle: ActionBarDrawerToggle
    private var binding: FragmentMainScreenSellerBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentMainScreenSellerBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val drawerLayout: DrawerLayout = binding!!.drawerLayout
        val navView: NavigationView
        return fragmentBinding.root
        //return inflater.inflate(R.layout.fragment_main_screen_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainScreenSellerFragment = this // setup data binding
    }

}