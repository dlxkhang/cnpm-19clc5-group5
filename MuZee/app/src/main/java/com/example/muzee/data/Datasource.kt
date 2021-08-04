package com.example.muzee.data

class Datasource {
    fun loadSellerOrder(): List<SellerOrder> {
        return listOf<SellerOrder>(
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Duong"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Duong"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Duong"),
            SellerOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Duong")
        )
    }

    fun loadOrder(): List<NormalUserOrder> {
        return listOf<NormalUserOrder>(
            NormalUserOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            NormalUserOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            NormalUserOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music"),
            NormalUserOrder("1234", "341 khuong viet", loadProduct(),700.0, 50.5, "On-going", "Khang Music")
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

    private lateinit var list_product_your_shop:List<oldProduct>
    init {
        list_product_your_shop = listOf<oldProduct>(
            oldProduct(Category.Piano,"Nord Piano 4 88-Key Digital Piano", 2.999,"Tho Chung","Used"),
            oldProduct(Category.Bass,"Guitar Bass Yamaha TRBX174", 200.0,"Xuan Khang","new"),
            oldProduct(Category.Electronic,"Yamaha Revstar RS620 Electronic Guitar", 714.0,"Tho Chung","new"),
            oldProduct(Category.Piano,"Nord1 Piano 4 88-Key Digital Piano", 2.999,"Nguyen","Like new"),
            oldProduct(Category.Guitar,"Guitar1 Bass Yamaha TRBX174", 200.0,"Xuan Khang","new"),
            oldProduct(Category.Electronic,"Yamaha1 Revstar RS620 Electronic Guitar", 714.0,"Nguyen","new"),
            oldProduct(Category.Guitar,"Nord2 Piano 4 88-Key Digital Piano", 2.999,"Ba Huy","new"),
            oldProduct(Category.Bass,"Guitar2 Bass Yamaha TRBX174", 200.0,"Nguyen","Used"),
            oldProduct(Category.Electronic,"Yamaha2 Revstar RS620 Electronic Guitar", 714.0,"Xuan Khang","new"),
            oldProduct(Category.Drum,"Yamaha1 Revstar RS620 Drum", 714.0,"Nguyen","new"),
            oldProduct(Category.Organ,"Nord21 88-Key Organ", 89.99,"Ba Huy","new"),)
    }
    fun loadOldProduct(): List<oldProduct> = list_product_your_shop
    fun addAnNewItem(oldProduct: oldProduct){
        list_product_your_shop.plus(oldProduct)
    }
}