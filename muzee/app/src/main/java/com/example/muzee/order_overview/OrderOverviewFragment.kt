package com.example.muzee.order_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.databinding.FragmentOrderOverviewBinding


class OrderOverviewFragment : Fragment() {

    private val args: OrderOverviewFragmentArgs by navArgs()

    private var binding: FragmentOrderOverviewBinding? = null // binding fragment_seller_order.xml

    private val viewModel: OrderOverviewViewModel by activityViewModels { OrderOverviewViewModelFactory(args.NID, args.normalUser, requireNotNull(activity).application) }

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
        viewModel.getOrders()
        binding!!.orderViewModel = viewModel


        binding!!.recyclerView.adapter = OrderOverviewAdapter(OrderOverviewAdapter.OnClickListener {
            viewModel.displayOrderDetail(it)
        })

        viewModel.navigateToSelectedOrder.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(OrderOverviewFragmentDirections.actionOrderOverviewFragmentToOrderDetailFragment(it, args.NID))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return fragmentBinding.root
    }
}
