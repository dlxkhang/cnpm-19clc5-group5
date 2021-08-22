package com.example.muzee.cart

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.R
import com.example.muzee.databinding.ProductItemCartBinding
import com.example.muzee.network.CartProduct

class CartAdapter (val viewModel: CartViewModel) :
    ListAdapter<CartProduct, CartAdapter.CartViewHolder>(DiffCallback) {

    class CartViewHolder(
        private var binding: ProductItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProduct) {
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

        holder.itemView.findViewById<View>(R.id.delete_button).setOnClickListener {
            var builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete product!")
            builder.setMessage("Sure?")
            builder.setNegativeButton("Yes") { dialog, id ->
                viewModel.deleteProductFromCart(item.PID)
            }
            builder.setPositiveButton("No") { dialog, id -> print(0) }
            var alert: AlertDialog = builder.create()
            alert.show()
        }

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