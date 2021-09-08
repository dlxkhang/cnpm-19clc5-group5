package com.example.muzee.oldproduct_overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.databinding.OldProductToBuyItemBinding
import com.example.muzee.network.OldProduct

class OldProductOverviewAdapter(val viewModel:OldProductOverviewViewModel,val onClickListener: OldProductOverviewAdapter.OnClickListener):
    ListAdapter<OldProduct,OldProductOverviewAdapter.OldProductViewHolder>(DiffCallback){
    class OldProductViewHolder(
        private var binding: OldProductToBuyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: OldProduct) {
            binding.oldproduct = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OldProductViewHolder {
        return OldProductViewHolder(
            OldProductToBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: OldProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OldProduct>() {
        override fun areItemsTheSame(oldItem: OldProduct, newItem: OldProduct): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: OldProduct, newItem: OldProduct): Boolean {
            return oldItem.productName == newItem.productName
        }
    }

    class OnClickListener(val clickListener: (product: OldProduct) -> Unit) {
        fun onClick(product: OldProduct) = clickListener(product)
    }
}