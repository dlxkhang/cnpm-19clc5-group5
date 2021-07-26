package com.example.muzee.data

abstract class Order (
    val orderId: String,
    val orderAddress: String,
    //val orderedProducts: List<Product>,
    val itemTotal: Double,
    val deliveryCharges: Double,
    var orderStatus: String
) {

    fun totalPrice(): Double {
        return itemTotal + deliveryCharges
    }
}

class SellerOrder (
    orderId: String,
    orderAddress: String,
    //orderedProducts: List<Product>,
    itemTotal: Double,
    deliveryCharges: Double,
    orderStatus: String,
    val storeName: String
) : Order(orderId, orderAddress, itemTotal, deliveryCharges, orderStatus)

class NormalUserOrder (
    orderId: String,
    orderAddress: String,
    //orderedProducts: List<Product>,
    itemTotal: Double,
    deliveryCharges: Double,
    orderStatus: String,
    val customerName: String,
) : Order(orderId, orderAddress, itemTotal, deliveryCharges, orderStatus)