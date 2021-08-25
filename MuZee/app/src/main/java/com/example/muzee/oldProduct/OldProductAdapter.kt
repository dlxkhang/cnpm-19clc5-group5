package com.example.muzee.oldProduct

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.R
import com.example.muzee.databinding.OldProductItemsBinding
import com.example.muzee.network.OldProduct

class OldProductAdapter(private  val onClickListener: OnClickListener, private val viewModel: OldProductViewModel) :
    ListAdapter<OldProduct, OldProductAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(
        private var binding: OldProductItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(old_product: OldProduct) {
            binding.oldproduct = old_product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            OldProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.itemView.findViewById<View>(R.id.edit_btn).setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.itemView.findViewById<View>(R.id.delete_btn).setOnClickListener {
            var builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete product!")
            builder.setMessage("Sure?")
            builder.setNegativeButton("Yes") { dialog, id ->
                viewModel.deleteAnOldProduct(item.productId)
                viewModel.getOldProducts()
            }
            builder.setPositiveButton("No") { dialog, id -> print(0) }
            var alert: AlertDialog = builder.create()
            alert.show()
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OldProduct>() {
        override fun areItemsTheSame(oldItem: OldProduct, newItem: OldProduct): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: OldProduct, newItem: OldProduct): Boolean {
            return (oldItem.productCategory==newItem.productCategory)
                    &&(oldItem.sellerName == newItem.sellerName)
                    &&(oldItem.condition==oldItem.condition)
                    &&(oldItem.imageURI == newItem.imageURI)
                    &&(oldItem.productDescription == newItem.productDescription)
                    &&(oldItem.NID == newItem.NID)
                    &&(oldItem.productId == newItem.productId)
        }
    }

    class OnClickListener(val clickListener: (oldproduct: OldProduct) -> Unit) {
        fun onClick(oldproduct: OldProduct) = clickListener(oldproduct)
    }
}