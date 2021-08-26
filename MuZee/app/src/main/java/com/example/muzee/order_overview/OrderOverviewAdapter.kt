package com.example.muzee.order_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.OrderItemBinding
import com.example.muzee.network.UserOrder

class OrderOverviewAdapter(private  val onClickListener: OnClickListener) :
    ListAdapter<UserOrder, OrderOverviewAdapter.OrderViewHolder>(DiffCallback) {

    class OrderViewHolder(
        private var binding: OrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: UserOrder) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserOrder>() {
        override fun areItemsTheSame(oldItem: UserOrder, newItem: UserOrder): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: UserOrder, newItem: UserOrder): Boolean {
            return oldItem.orderId == newItem.orderId
                    && oldItem.orderAddress == newItem.orderAddress
                    && oldItem.orderedProducts == newItem.orderedProducts
                    && oldItem.itemTotal == newItem.itemTotal
                    && oldItem.deliveryCharges == newItem.deliveryCharges
                    && oldItem.orderStatus == newItem.orderStatus
                    && oldItem.customerName == newItem.customerName
        }
    }

    class OnClickListener(val clickListener: (order: UserOrder) -> Unit) {
        fun onClick(order: UserOrder) = clickListener(order)
    }
}
