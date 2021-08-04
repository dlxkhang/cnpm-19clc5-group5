package com.example.muzee

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.cart.CartAdapter
import com.example.muzee.data.Product
import com.example.muzee.payment.PaymentAdapter
import com.example.muzee.productoverview.ProductOverviewAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductOverviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData1")
fun bindRecyclerView1(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as CartAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataPayment")
fun bindRecyclerViewPayment(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as PaymentAdapter
    adapter.submitList(data)
}