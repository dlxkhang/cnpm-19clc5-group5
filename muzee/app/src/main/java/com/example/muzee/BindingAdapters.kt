package com.example.muzee

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzee.cart.CartAdapter
import com.example.muzee.network.UserOrder
import com.example.muzee.network.CartProduct
import com.example.muzee.network.NewProduct
import com.example.muzee.network.OldProduct
import com.example.muzee.network.seller.order.Order_responseItem
import com.example.muzee.network.seller.order.OrderedProduct
import com.example.muzee.network.seller.product.ProductSeller
import com.example.muzee.oldProduct.OldProductAdapter
import com.example.muzee.oldproduct_overview.OldProductOverviewAdapter
import com.example.muzee.order_detail.OrderDetailAdapter
import com.example.muzee.order_overview.OrderOverviewAdapter
import com.example.muzee.payment.PaymentAdapter
import com.example.muzee.productoverview.ProductOverviewAdapter
import com.example.muzee.seller.order_detail.SellerOrderDetailAdapter
import com.example.muzee.seller.order_overview.SellerOrderAdapter
import com.example.muzee.seller.product_overview.SellerProductOverviewAdapter

@BindingAdapter("listData")
fun bindRecyclerViewProductOverview(recyclerView: RecyclerView, data: List<NewProduct>?) {
    val adapter = recyclerView.adapter as ProductOverviewAdapter
    adapter.submitList(data)
}
@BindingAdapter("listOldProduct")
fun bindRecyclerViewOldProductOverview(recyclerView: RecyclerView,data:List<OldProduct>?){
    val adapter = recyclerView.adapter as OldProductOverviewAdapter
    adapter.submitList(data)
}
@BindingAdapter("listData1")
fun bindRecyclerViewProductCart(recyclerView: RecyclerView, data: List<CartProduct>?) {
    val adapter = recyclerView.adapter as CartAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataPayment")
fun bindRecyclerViewPayment(recyclerView: RecyclerView, data: List<CartProduct>?) {
    val adapter = recyclerView.adapter as PaymentAdapter
    adapter.submitList(data)
}

@BindingAdapter("listSellerOrder")
fun bindRecyclerViewSellerOrder(recyclerView: RecyclerView, data: List<Order_responseItem>?) {
    val adapter = recyclerView.adapter as SellerOrderAdapter
    adapter.submitList(data)
}

@BindingAdapter("listOrder")
fun bindRecyclerViewOrder(recyclerView: RecyclerView, data: List<UserOrder>?) {
    val adapter = recyclerView.adapter as OrderOverviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("listProductSeller")
fun bindRecyclerViewSellerProduct(recyclerView: RecyclerView, data: List<ProductSeller>?) {
    val adapter = recyclerView.adapter as SellerProductOverviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("listProductSellerOrderDetail")
fun bindRecyclerViewSellerOrderDetail(recyclerView: RecyclerView, data: List<OrderedProduct>?) {
    val adapter = recyclerView.adapter as SellerOrderDetailAdapter
    adapter.submitList(data)
}

@BindingAdapter("listProductOrderDetail")
fun bindRecyclerViewOrderDetail(recyclerView: RecyclerView, data: List<NewProduct>?) {
    val adapter = recyclerView.adapter as OrderDetailAdapter
    adapter.submitList(data)
}

@BindingAdapter("listOldData")
fun bindRecyclerViewOldProduct(recyclerView: RecyclerView, data: List<OldProduct>?) {
    val adapter = recyclerView.adapter as OldProductAdapter
    adapter.submitList(data)
}