package com.example.muzee.seller.order_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.OrderSellerItemBinding
import com.example.muzee.network.seller.order.Order_responseItem

class SellerOrderAdapter(private  val onClickListener: OnClickListener) :
    ListAdapter<Order_responseItem, SellerOrderAdapter.OrderViewHolder>(DiffCallback) {

    class OrderViewHolder(
        private var binding: OrderSellerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order_responseItem) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            OrderSellerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Order_responseItem>() {
        override fun areItemsTheSame(oldItem: Order_responseItem, newItem: Order_responseItem): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order_responseItem, newItem: Order_responseItem): Boolean {
            return oldItem.orderId == newItem.orderId
                    && oldItem.orderAddress == newItem.orderAddress
                    && oldItem.orderedProducts == newItem.orderedProducts
                    && oldItem.itemTotal == newItem.itemTotal
                    && oldItem.deliveryCharges == newItem.deliveryCharges
                    && oldItem.orderStatus == newItem.orderStatus
                    && oldItem.customerName == newItem.customerName
        }
    }

    class OnClickListener(val clickListener: (order: Order_responseItem) -> Unit) {
        fun onClick(order: Order_responseItem) = clickListener(order)
    }
}
