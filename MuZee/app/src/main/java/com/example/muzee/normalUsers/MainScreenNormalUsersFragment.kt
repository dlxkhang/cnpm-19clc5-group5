package com.example.muzee.normalUsers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentMainScreenNormalUsersBinding

class MainScreenNormalUsersFragment : Fragment() {
    lateinit var drawer_layout:DrawerLayout // drawerLayout contain nav menu in xml
    lateinit var toggle: ActionBarDrawerToggle // toggle button
    private var binding:FragmentMainScreenNormalUsersBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set up data binding
        val fragmentbinding = FragmentMainScreenNormalUsersBinding.inflate(inflater,container,false)
        binding = fragmentbinding

        setHasOptionsMenu(true)

        // binding drawerLayout from xml
        drawer_layout = binding!!.drawerLayoutNormal

        //init toggle button for menu
        toggle = ActionBarDrawerToggle(activity,drawer_layout, R.string.open,R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val activity = activity as AppCompatActivity?// get activity
        activity!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)// make toggle button visible
        //activity!!.supportActionBar?.setHomeButtonEnabled(true)
        activity!!.supportActionBar?.setTitle(R.string.app_name)// set title text for main screen

        //handle onClick event on menu Items
        binding!!.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeItem_normalUser ->{
                    drawer_layout.closeDrawers()
                }
                R.id.categoriesItem->{
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_categoryFragment)

                }
                R.id.cart_item->{
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_cartFragment)
                }
                //R.id.myOrderItem->{true}
                //R.id.myShopItem->{true}

                //R.id.switchingModeItem->{true}
                //R.id.customerSupportItem->{true}

            }
            true
        }

        return fragmentbinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainScreenNormalUsersFragment = this
    }
}