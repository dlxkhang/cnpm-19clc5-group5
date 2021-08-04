package com.example.muzee

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.muzee.data.oldProduct
import com.example.muzee.oldProduct.OldProductAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<oldProduct>?) {
    val adapter = recyclerView.adapter as OldProductAdapter
    adapter.submitList(data)
}