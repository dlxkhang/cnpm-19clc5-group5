package com.example.muzee

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.data.Product
import com.example.muzee.data.SellerOrder
import com.example.muzee.seller.order_detail.SellerOrderDetailAdapter
import com.example.muzee.seller.product_overview.SellerProductOverviewAdapter
import com.example.muzee.seller.order_overview.SellerOrderAdapter

@BindingAdapter("listSellerOrder")
fun bindRecyclerViewSellerOrder(recyclerView: RecyclerView, data: List<SellerOrder>?) {
    val adapter = recyclerView.adapter as SellerOrderAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindRecyclerViewSellerProduct(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as SellerProductOverviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("listProductSellerOrderDetail")
fun bindRecyclerViewPayment(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as SellerOrderDetailAdapter
    adapter.submitList(data)
}