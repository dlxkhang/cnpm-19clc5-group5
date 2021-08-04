package com.example.muzee.order_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.NormalUserOrder
import com.example.muzee.databinding.OrderItemBinding

class OrderOverviewAdapter(private  val onClickListener: OnClickListener) :
    ListAdapter<NormalUserOrder, OrderOverviewAdapter.OrderViewHolder>(DiffCallback) {

    class OrderViewHolder(
        private var binding: OrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: NormalUserOrder) {
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

    companion object DiffCallback : DiffUtil.ItemCallback<NormalUserOrder>() {
        override fun areItemsTheSame(oldItem: NormalUserOrder, newItem: NormalUserOrder): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: NormalUserOrder, newItem: NormalUserOrder): Boolean {
            return oldItem.orderId == newItem.orderId
                    && oldItem.orderAddress == newItem.orderAddress
                    && oldItem.orderedProducts == newItem.orderedProducts
                    && oldItem.itemTotal == newItem.itemTotal
                    && oldItem.deliveryCharges == newItem.deliveryCharges
                    && oldItem.orderStatus == newItem.orderStatus
                    && oldItem.storeName == newItem.storeName
        }
    }

    class OnClickListener(val clickListener: (order: NormalUserOrder) -> Unit) {
        fun onClick(order: NormalUserOrder) = clickListener(order)
    }
}
