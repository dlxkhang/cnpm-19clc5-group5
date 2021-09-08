package com.example.muzee.seller.product_overview

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.R
import com.example.muzee.databinding.ProductSellerItemsBinding
import com.example.muzee.network.seller.product.ProductSeller

class SellerProductOverviewAdapter (private  val onClickListener: OnClickListener,
                                    private val viewModel: SellerProductOverviewViewModel,
                                    private val fragment:SellerProductOverviewFragment,private val sellerID:String?) :
    ListAdapter<ProductSeller, SellerProductOverviewAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(
        private var binding: ProductSellerItemsBinding
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
            ProductSellerItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.itemView.findViewById<View>(R.id.btn_edit).setOnClickListener{
            fragment.findNavController().navigate(SellerProductOverviewFragmentDirections.actionSellerProductOverviewFragmentToEditNewProductFragment(item,sellerID,fragment.args.sellerInfo))
        }
        holder.itemView.findViewById<View>(R.id.btn_delete).setOnClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete product!")
            builder.setMessage("Sure?")
            builder.setPositiveButton("Yes") { dialog, id ->

                    viewModel.deleteProduct(item.productId!!)
                    viewModel.getNewProducts()

            }
            builder.setNegativeButton("No") { dialog, id -> dialog.cancel() }
            val alert: AlertDialog = builder.create()
            alert.show()
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