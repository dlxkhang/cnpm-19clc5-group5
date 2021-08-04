package com.example.muzee.oldProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.oldProduct
import com.example.muzee.databinding.OldProductItemsBinding

class OldProductAdapter(private  val onClickListener: OnClickListener) :
    ListAdapter<oldProduct, OldProductAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(
        private var binding: OldProductItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(old_product: oldProduct) {
            binding.oldproduct = old_product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            OldProductItemsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<oldProduct>() {
        override fun areItemsTheSame(oldItem: oldProduct, newItem: oldProduct): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: oldProduct, newItem: oldProduct): Boolean {
            return (oldItem.productPrice == newItem.productPrice)
                    &&(oldItem.productCategory==newItem.productCategory)
                    &&(oldItem.productPrice==newItem.productPrice)
                    &&(oldItem.sellerName == newItem.sellerName)
                    &&(oldItem.condidtion==oldItem.condidtion)
                    &&(oldItem.imageURI == newItem.imageURI)
                    &&(oldItem.productDescription == newItem.productDescription)
        }
    }

    class OnClickListener(val clickListener: (oldproduct: oldProduct) -> Unit) {
        fun onClick(oldproduct: oldProduct) = clickListener(oldproduct)
    }
}