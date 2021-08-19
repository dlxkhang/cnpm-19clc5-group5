package com.example.muzee.order_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.databinding.FragmentOrderOverviewBinding


class OrderOverviewFragment : Fragment() {

    private val viewModel: OrderOverviewViewModel by viewModels()
    private var binding: FragmentOrderOverviewBinding? = null // binding fragment_seller_order.xml
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup data binding
        val fragmentBinding = FragmentOrderOverviewBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding!!.lifecycleOwner = this

        // Giving the binding access to the OrderViewModel
        binding!!.orderViewModel = viewModel


        binding!!.recyclerView.adapter = OrderOverviewAdapter(OrderOverviewAdapter.OnClickListener {
            viewModel.displayOrderDetail(it)
        })

        viewModel.navigateToSelectedOrder.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(OrderOverviewFragmentDirections.actionOrderOverviewFragmentToOrderDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return fragmentBinding.root
    }
}
