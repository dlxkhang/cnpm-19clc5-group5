package com.example.muzee.seller.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.ProductItemOrderDetailSellerBinding
import com.example.muzee.network.seller.order.OrderedProduct

class SellerOrderDetailAdapter :
    ListAdapter<OrderedProduct, SellerOrderDetailAdapter.SellerOrderDetailViewHolder>(DiffCallback) {

    class SellerOrderDetailViewHolder(
        private var binding: ProductItemOrderDetailSellerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: OrderedProduct) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellerOrderDetailAdapter.SellerOrderDetailViewHolder {
        return SellerOrderDetailAdapter.SellerOrderDetailViewHolder (
            ProductItemOrderDetailSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SellerOrderDetailAdapter.SellerOrderDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderedProduct>() {
        override fun areItemsTheSame(oldItem: OrderedProduct, newItem: OrderedProduct): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: OrderedProduct, newItem: OrderedProduct): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }
}
