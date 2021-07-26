package com.example.muzee

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.SellerOrder
import com.example.muzee.seller.OrderAdapter

@BindingAdapter("listSellerOrder")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SellerOrder>?) {
    val adapter = recyclerView.adapter as OrderAdapter
    adapter.submitList(data)
}