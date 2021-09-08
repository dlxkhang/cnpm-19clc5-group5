package com.example.muzee.productoverview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.R
import com.example.muzee.databinding.NewProductItemBinding
import com.example.muzee.network.NewProduct

class ProductOverviewAdapter(val viewModel: ProductOverviewViewModel, val onClickListener: OnClickListener) :
    ListAdapter<NewProduct, ProductOverviewAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(
        private var binding: NewProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: NewProduct) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            NewProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.itemView.findViewById<View>(R.id.add2cart_button).setOnClickListener {
            viewModel.addProductToCart(item)
            Toast.makeText(holder.itemView.context, "Product has been added to cart", Toast.LENGTH_LONG).show()
        }
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

    class OnClickListener(val clickListener: (product: NewProduct) -> Unit) {
        fun onClick(product: NewProduct) = clickListener(product)
    }
}