package com.example.muzee.normalUsers

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentMainScreenNormalUsersBinding

class MainScreenNormalUsersFragment : Fragment() {
    private lateinit var drawer_layout: DrawerLayout // drawerLayout contain nav menu in xml
    lateinit var toggle: ActionBarDrawerToggle // toggle button
    private var binding: FragmentMainScreenNormalUsersBinding? = null
    private var isOpenMyStore: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //set up data binding
        val fragmentbinding =
            FragmentMainScreenNormalUsersBinding.inflate(inflater, container, false)
        binding = fragmentbinding

        setHasOptionsMenu(true)

        // binding drawerLayout from xml
        drawer_layout = binding!!.drawerLayoutNormal

        //init toggle button for menu
        toggle = ActionBarDrawerToggle(activity, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val activity = activity as AppCompatActivity?// get activity
        activity!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)// make toggle button visible
        //activity!!.supportActionBar?.setHomeButtonEnabled(true)
        activity.supportActionBar?.setTitle(R.string.app_name)// set title text for main screen
        //handle onClick event on menu Items
        binding!!.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeItem_normalUser -> {
                    drawer_layout.closeDrawers()
                }
                R.id.categoriesItem -> {
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_categoryFragment)

                }
                R.id.cart_item -> {
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_cartFragment)
                }
                //R.id.myOrderItem->{true}
                R.id.myShopItem -> {
//                    val productItem = binding!!.navView.menu.findItem(R.id.productItem)
//                    val offersItem = binding!!.navView.menu.findItem(R.id.offersItem)
//                    productItem.isVisible = !productItem.isVisible
//                    offersItem.isVisible = !offersItem.isVisible
//                    offersItem?.setVisible(offersItem.isVisible)
//                    productItem?.setVisible(productItem.isVisible)
                    isOpenMyStore = !isOpenMyStore
                    invalidateOptionsMenu(activity)
                }
                R.id.productItem -> {
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_oldProductStoreFragment)
                }

                //R.id.switchingModeItem->{true}
                //R.id.customerSupportItem->{true}

            }
            true
        }

        binding?.navView?.findViewById<Switch>(R.id.btn_switch_mode)?.setOnCheckedChangeListener({_,isChecked ->
            if(isChecked){
                binding?.navView?.menu?.findItem(R.id.switchMode)?.title = getString(R.string.old_shopping)
            }
            else {
                binding?.navView?.menu?.findItem(R.id.switchMode)?.title = getString(R.string.new_shopping)
            }
        })

        return fragmentbinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_actionbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.cart_item) {
            findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_cartFragment)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val myShopItem = binding?.navView?.menu?.findItem(R.id.myShopItem)
        val productItem = binding?.navView?.menu?.findItem(R.id.productItem)
        val offersItem = binding?.navView?.menu?.findItem(R.id.offersItem)
        if (isOpenMyStore == true) {
            myShopItem?.actionView?.findViewById<ImageView>(R.id.dropdown_arrow)
                ?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
        } else {
            myShopItem?.actionView?.findViewById<ImageView>(R.id.dropdown_arrow)
                ?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)/**/
        }
        productItem?.setVisible(isOpenMyStore)
        offersItem?.setVisible(isOpenMyStore)
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainScreenNormalUsersFragment = this
    }
}