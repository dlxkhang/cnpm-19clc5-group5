package com.example.muzee.seller.order_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.SellerOrder
import com.example.muzee.databinding.OrderSellerItemBinding

class SellerOrderAdapter(private  val onClickListener: OnClickListener) :
    ListAdapter<SellerOrder, SellerOrderAdapter.OrderViewHolder>(DiffCallback) {

    class OrderViewHolder(
        private var binding: OrderSellerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: SellerOrder) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            OrderSellerItemBinding.inflate(LayoutInflater.from(parent.context))

        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SellerOrder>() {
        override fun areItemsTheSame(oldItem: SellerOrder, newItem: SellerOrder): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: SellerOrder, newItem: SellerOrder): Boolean {
            return oldItem.orderId == newItem.orderId
                    && oldItem.orderAddress == newItem.orderAddress
                    && oldItem.orderedProducts == newItem.orderedProducts
                    && oldItem.itemTotal == newItem.itemTotal
                    && oldItem.deliveryCharges == newItem.deliveryCharges
                    && oldItem.orderStatus == newItem.orderStatus
                    && oldItem.storeName == newItem.storeName
        }
    }

    class OnClickListener(val clickListener: (order: SellerOrder) -> Unit) {
        fun onClick(order: SellerOrder) = clickListener(order)
    }
}
