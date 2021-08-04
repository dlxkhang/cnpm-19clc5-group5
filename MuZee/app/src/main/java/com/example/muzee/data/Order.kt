package com.example.muzee.data
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Order (
    val orderId: String,
    val orderAddress: String,
    val orderedProducts: List<Product>,
    val itemTotal: Double,
    val deliveryCharges: Double,
    var orderStatus: String
): Parcelable {

    fun totalPrice(): Double {
        return itemTotal + deliveryCharges
    }
}

class SellerOrder(
    orderId: String,
    orderAddress: String,
    orderedProducts: List<Product>,
    itemTotal: Double,
    deliveryCharges: Double,
    orderStatus: String,
    val customerName: String?
) : Order(orderId, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus) {
    constructor(parcel: Parcel) : this(
        TODO("orderId"),
        TODO("orderAddress"),
        TODO("orderedProducts"),
        TODO("itemTotal"),
        TODO("deliveryCharges"),
        TODO("orderStatus"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(customerName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SellerOrder> {
        override fun createFromParcel(parcel: Parcel): SellerOrder {
            return SellerOrder(parcel)
        }

        override fun newArray(size: Int): Array<SellerOrder?> {
            return arrayOfNulls(size)
        }
    }
}

class NormalUserOrder(
    orderId: String,
    orderAddress: String,
    orderedProducts: List<Product>,
    itemTotal: Double,
    deliveryCharges: Double,
    orderStatus: String,
    val storeName: String?,
) : Order(orderId, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus) {
    constructor(parcel: Parcel) : this(
        TODO("orderId"),
        TODO("orderAddress"),
        TODO("orderedProducts"),
        TODO("itemTotal"),
        TODO("deliveryCharges"),
        TODO("orderStatus"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(storeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NormalUserOrder> {
        override fun createFromParcel(parcel: Parcel): NormalUserOrder {
            return NormalUserOrder(parcel)
        }

        override fun newArray(size: Int): Array<NormalUserOrder?> {
            return arrayOfNulls(size)
        }
    }
}