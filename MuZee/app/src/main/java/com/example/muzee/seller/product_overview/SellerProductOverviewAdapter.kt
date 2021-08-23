package com.example.muzee.seller.product_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.Product
import com.example.muzee.databinding.ProductItemBinding
import com.example.muzee.network.seller.product.ProductSeller

class SellerProductOverviewAdapter (private  val onClickListener: OnClickListener) :
    ListAdapter<ProductSeller, SellerProductOverviewAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(
        private var binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductSeller) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProductSeller>() {
        override fun areItemsTheSame(oldItem: ProductSeller, newItem: ProductSeller): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: ProductSeller, newItem: ProductSeller): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }

    class OnClickListener(val clickListener: (product: ProductSeller) -> Unit) {
        fun onClick(product: ProductSeller) = clickListener(product)
    }
}