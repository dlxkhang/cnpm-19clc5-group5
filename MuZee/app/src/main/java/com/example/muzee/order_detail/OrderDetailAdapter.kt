package com.example.muzee.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.Product
import com.example.muzee.databinding.ProductItemOrderDetailBinding

class OrderDetailAdapter :
    ListAdapter<Product, OrderDetailAdapter.OrderDetailViewHolder>(DiffCallback) {

    class OrderDetailViewHolder(
        private var binding: ProductItemOrderDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailAdapter.OrderDetailViewHolder {
        return OrderDetailAdapter.OrderDetailViewHolder (
            ProductItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.OrderDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productTitle == newItem.productTitle
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }
}
