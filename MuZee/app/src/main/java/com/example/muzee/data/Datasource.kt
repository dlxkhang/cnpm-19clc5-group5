package com.example.muzee.data

class Datasource {

    fun loadOrder(): List<SellerOrder> {
        return listOf<SellerOrder>(
            SellerOrder("1234", "341 khuong viet", 700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", 700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", 700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", 700.0, 50.5, "On-going", "Khang Music")
        )
    }
}