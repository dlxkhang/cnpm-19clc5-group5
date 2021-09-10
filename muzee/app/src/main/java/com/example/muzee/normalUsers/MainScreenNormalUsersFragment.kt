package com.example.muzee.normalUsers

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.databinding.FragmentMainScreenNormalUsersBinding
import com.example.muzee.productoverview.ProductOverviewAdapter
import com.example.muzee.productoverview.ProductOverviewViewModel
import com.example.muzee.productoverview.ProductOverviewViewModelFactory

class MainScreenNormalUsersFragment : Fragment() {
    private lateinit var drawer_layout: DrawerLayout // drawerLayout contain nav menu in xml
    lateinit var toggle: ActionBarDrawerToggle // toggle button
    private var binding: FragmentMainScreenNormalUsersBinding? = null
    private var isOpenMyStore: Boolean = false
    //private lateinit var viewModel:ProductOverviewViewModel
    private val viewModel: ProductOverviewViewModel by activityViewModels { ProductOverviewViewModelFactory(args.NID, args.normalUser ,requireNotNull(activity).application) }
    private val args: MainScreenNormalUsersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //set up data binding
        val fragmentbinding =
            FragmentMainScreenNormalUsersBinding.inflate(inflater, container, false)
        binding = fragmentbinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        fragmentbinding.lifecycleOwner = this

//        val viewModelFactory = ProductOverviewViewModelFactory(args.NID,args.normalUser, requireNotNull(activity).application)
//        viewModel = ViewModelProvider(this,viewModelFactory).get(ProductOverviewViewModel::class.java)
        // Giving the binding access to the OverviewViewModel
        if (viewModel.navigateFromLogin){
            viewModel.NID = args.NID
            viewModel.normalUser = args.normalUser
            viewModel.navigateFromLogin = false
        }

        if (viewModel.isSearchedCategory){

        }
        else {
            viewModel.getNewProducts()
        }
        fragmentbinding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        fragmentbinding.recyclerView.adapter = ProductOverviewAdapter(viewModel, ProductOverviewAdapter.OnClickListener {
            viewModel.displayProductDetail(it)
        })

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(MainScreenNormalUsersFragmentDirections.actionShowDetail(it, viewModel.NID))
                viewModel.displayPropertyDetailsComplete()
            }
        })


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
        binding?.navView?.getHeaderView(0)?.findViewById<TextView>(R.id.usernameLabel)?.text = viewModel.normalUser.fullname
        //handle onClick event on menu Items
        binding!!.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeItem_normalUser -> {
                    viewModel.getNewProducts()
                    drawer_layout.closeDrawers()
                }
                R.id.categoriesItem -> {
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_categoryFragment)
                }
                R.id.cart_item -> {
                    findNavController().navigate(MainScreenNormalUsersFragmentDirections.actionMainToCart(viewModel.NID, viewModel.normalUser))
                }
                R.id.myOrderItem->{
                    findNavController().navigate(MainScreenNormalUsersFragmentDirections.actionMainScreenNormalUsersFragmentToOrderOverviewFragment(viewModel.NID, viewModel.normalUser))
                }
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
                    findNavController().navigate(MainScreenNormalUsersFragmentDirections.actionMainScreenNormalUsersFragmentToOldProductStoreFragment(viewModel.NID))
                }
                R.id.logout_btn->{
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_loginFragment)
                    viewModel.navigateFromLogin = true
                }

                R.id.switchMode->{
                    findNavController().navigate(R.id.action_mainScreenNormalUsersFragment_to_oldProductOverviewFragment)
                }
                //R.id.customerSupportItem->{true}

            }
            true
        }



        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank() or query.isNullOrEmpty()) {
                    viewModel.getNewProducts()
                }
                else {
                    viewModel.searchProduct(query)
                }

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("")) {
                    if (viewModel.isSearchedCategory){
                        viewModel.isSearchedCategory = false
                    }
                    else {
                        this.onQueryTextSubmit("")
                    }

                }
                return true
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
            findNavController().navigate(MainScreenNormalUsersFragmentDirections.actionMainToCart(viewModel.NID, viewModel.normalUser))
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