package com.example.muzee.data

class Datasource {

    fun loadOrder(): List<SellerOrder> {
        return listOf<SellerOrder>(
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music")
        )
    }

    fun loadProduct(): List<Product> {
        return listOf<Product>(
            Product("Nord Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar Bass Yamaha TRBX174", 200.0),
            Product("Yamaha Revstar RS620 Electronic Guitar", 714.0),
            Product("Nord1 Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar1 Bass Yamaha TRBX174", 200.0),
            Product("Yamaha1 Revstar RS620 Electronic Guitar", 714.0),
            Product("Nord2 Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar2 Bass Yamaha TRBX174", 200.0),
            Product("Yamaha2 Revstar RS620 Electronic Guitar", 714.0),
            Product("Nord Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar Bass Yamaha TRBX174", 200.0),
            Product("Yamaha Revstar RS620 Electronic Guitar", 714.0),
            Product("Nord1 Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar1 Bass Yamaha TRBX174", 200.0),
            Product("Yamaha1 Revstar RS620 Electronic Guitar", 714.0),
            Product("Nord2 Piano 4 88-Key Digital Piano", 2.999),
            Product("Guitar2 Bass Yamaha TRBX174", 200.0),
            Product("Yamaha2 Revstar123 RS620 Electronic Guitar", 714.0)
        )
    }
}