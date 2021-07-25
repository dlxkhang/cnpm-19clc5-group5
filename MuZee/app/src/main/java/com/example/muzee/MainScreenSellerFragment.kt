package com.example.muzee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.muzee.databinding.FragmentMainScreenSellerBinding


class MainScreenSellerFragment : Fragment(){

    lateinit var toggle: ActionBarDrawerToggle
    private var binding: FragmentMainScreenSellerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup data binding
        val fragmentBinding = FragmentMainScreenSellerBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // binding drawerLayout and navView from xml files
        val drawerLayout: DrawerLayout = binding!!.drawerLayout

        // init toggle
        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        return fragmentBinding.root
        //return inflater.inflate(R.layout.fragment_main_screen_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainScreenSellerFragment = this // setup data binding
    }


}