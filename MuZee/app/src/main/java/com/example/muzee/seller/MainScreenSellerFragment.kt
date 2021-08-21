package com.example.muzee.seller


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Seller
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
        val seller: Seller = MainScreenSellerFragmentArgs.fromBundle(requireArguments()).seller
        binding?.navView?.getHeaderView(0)?.findViewById<TextView>(R.id.storeName_nav_head)?.text = seller.storeName
        binding?.navView?.getHeaderView(0)?.findViewById<TextView>(R.id.storeAddress_nav_head)?.text = seller.storeAddress
        binding?.storeNameMain?.text = seller.storeName
        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setDisplayHomeAsUpEnabled(true) // make toggle button visible

        activity!!.supportActionBar?.setTitle("Home") // set title text for main screen

        // handle onClick event on menu items
        binding!!.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> {
                        drawerLayout.closeDrawers() // close menu and stay at home screen
                    }
                    R.id.myProductItem -> {
                        findNavController().navigate(R.id.action_mainScreenSellerFragment_to_sellerProductOverviewFragment) // move to product screen
                    }
                    R.id.orderListItem -> {
                        findNavController().navigate(R.id.action_mainScreenSellerFragment_to_sellerOrderFragment) // move to order list screen
                    }
                    R.id.logout_btn->{
                        findNavController().navigate(R.id.action_mainScreenSellerFragment_to_loginFragment)
                    }
                }
            true
        }
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