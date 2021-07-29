package com.example.muzee.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.Product
import com.example.muzee.databinding.ProductItemCartBinding

class CartAdapter :
    ListAdapter<Product, CartAdapter.CartViewHolder>(DiffCallback) {

    class CartViewHolder(
        private var binding: ProductItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartViewHolder {
        return CartAdapter.CartViewHolder(
            ProductItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
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