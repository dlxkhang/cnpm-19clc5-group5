package com.example.muzee


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.muzee.databinding.FragmentMainScreenSellerBinding


class MainScreenSellerFragment : Fragment(){

    lateinit var drawerLayout : DrawerLayout // drawerLayout contain nav menu in xml
    lateinit var toggle: ActionBarDrawerToggle // toggle button
    private var binding: FragmentMainScreenSellerBinding? = null // binding fragment_main_screen_seller.xml

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup data binding
        val fragmentBinding = FragmentMainScreenSellerBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        setHasOptionsMenu(true)

        drawerLayout = binding!!.drawerLayout // binding drawerLayout from xml

        // init toggle button for menu
        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setDisplayHomeAsUpEnabled(true) // make toggle button visible

        return fragmentBinding.root
    }

    // in case toggle button is pressed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainScreenSellerFragment = this // setup data binding
    }

}