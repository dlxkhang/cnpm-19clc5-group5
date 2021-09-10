package com.example.muzee.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.ProductUserOrderItemBinding
import com.example.muzee.network.NewProduct

class OrderDetailAdapter :
    ListAdapter<NewProduct, OrderDetailAdapter.OrderDetailViewHolder>(DiffCallback) {

    class OrderDetailViewHolder(
        private var binding: ProductUserOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: NewProduct) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailAdapter.OrderDetailViewHolder {
        return OrderDetailAdapter.OrderDetailViewHolder (
            ProductUserOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.OrderDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewProduct>() {
        override fun areItemsTheSame(oldItem: NewProduct, newItem: NewProduct): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: NewProduct, newItem: NewProduct): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }
}
