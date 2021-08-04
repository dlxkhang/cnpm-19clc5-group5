package com.example.muzee.seller.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.Product
import com.example.muzee.databinding.ProductItemOrderDetailBinding

class SellerOrderDetailAdapter :
    ListAdapter<Product, SellerOrderDetailAdapter.SellerOrderDetailViewHolder>(DiffCallback) {

    class SellerOrderDetailViewHolder(
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
    ): SellerOrderDetailAdapter.SellerOrderDetailViewHolder {
        return SellerOrderDetailAdapter.SellerOrderDetailViewHolder (
            ProductItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SellerOrderDetailAdapter.SellerOrderDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }
}
