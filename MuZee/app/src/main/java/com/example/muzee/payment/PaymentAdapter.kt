package com.example.muzee.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.ProductItemPaymentBinding
import com.example.muzee.network.CartProduct

class PaymentAdapter :
    ListAdapter<CartProduct, PaymentAdapter.PaymentViewHolder>(DiffCallback) {

    class PaymentViewHolder(
        private var binding: ProductItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProduct) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentAdapter.PaymentViewHolder {
        return PaymentAdapter.PaymentViewHolder(
            ProductItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PaymentAdapter.PaymentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.productPrice == newItem.productPrice
        }
    }
}