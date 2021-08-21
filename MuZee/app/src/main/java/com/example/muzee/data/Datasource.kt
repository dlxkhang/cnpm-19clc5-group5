package com.example.muzee.data

class Datasource {

    private lateinit var list_product_your_shop: List<oldProduct>

    init {
        list_product_your_shop = listOf<oldProduct>(
            oldProduct(
                "123",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                8
            ),
            oldProduct("321",
                Category.Piano,
                "Nord1 Piano 4 88-Key Digital Piano",
                2.999,
                "Nguyen",
                5
            ),
            oldProduct("543",
                Category.Guitar,
                "Nord2 Piano 4 88-Key Digital Piano",
                2.999,
                "Ba Huy",
                9
            )
        )
    }

    fun loadOldProduct(): List<oldProduct> = list_product_your_shop
    fun addAnNewItem(oldProduct: oldProduct) {
        list_product_your_shop.plus(oldProduct)
    }

    fun loadSellerOrder(): List<SellerOrder> {
        return listOf<SellerOrder>(
            SellerOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Duong"
            ),
            SellerOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Duong"
            ),
            SellerOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Duong"
            ),
            SellerOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Duong"
            )
        )
    }

    fun loadOrder(): List<NormalUserOrder> {
        return listOf<NormalUserOrder>(
            NormalUserOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Music"
            ),
            NormalUserOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Music"
            ),
            NormalUserOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Music"
            ),
            NormalUserOrder(
                "1234",
                "341 khuong viet",
                loadProduct(),
                700.0,
                50.5,
                "On-going",
                "Khang Music"
            )
        )
    }

    fun loadProduct(): List<Product> {
        return listOf<Product>(
            Product("12",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2999.0,
                "Tho Chung",
                "test"
            ),
            Product("3319",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2999.0,
                "Tho Chung",
                "test"
            ),
            Product("342",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2999.0,
                "Tho Chung",
                "test"
            ),
            Product("255",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2999.0,
                "Tho Chung",
                "test"
            ),
            Product("322",
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2999.0,
                "Tho Chung",
                "test"
            ),
            Product("142",Category.Piano, "Nord Piano 4 88-Key Digital Piano", 2.999, "Tho Chung", "test")
        )
    }
}