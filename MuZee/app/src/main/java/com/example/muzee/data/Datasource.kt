package com.example.muzee.data

class Datasource {

    private lateinit var list_product_your_shop: List<oldProduct>

    init {
        list_product_your_shop = listOf<oldProduct>(
            oldProduct(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                8
            ),
            oldProduct(Category.Bass, "Guitar Bass Yamaha TRBX174", 200.0, "Xuan Khang", 7),
            oldProduct(
                Category.Electronic,
                "Yamaha Revstar RS620 Electronic Guitar",
                714.0,
                "Tho Chung",
                6
            ),
            oldProduct(
                Category.Piano,
                "Nord1 Piano 4 88-Key Digital Piano",
                2.999,
                "Nguyen",
                5
            ),
            oldProduct(Category.Guitar, "Guitar1 Bass Yamaha TRBX174", 200.0, "Xuan Khang", 9),
            oldProduct(
                Category.Electronic,
                "Yamaha1 Revstar RS620 Electronic Guitar",
                714.0,
                "Nguyen",
                4
            ),
            oldProduct(
                Category.Guitar,
                "Nord2 Piano 4 88-Key Digital Piano",
                2.999,
                "Ba Huy",
                9
            ),
            oldProduct(Category.Bass, "Guitar2 Bass Yamaha TRBX174", 200.0, "Nguyen", 10),
            oldProduct(
                Category.Electronic,
                "Yamaha2 Revstar RS620 Electronic Guitar",
                714.0,
                "Xuan Khang",
                9
            ),
            oldProduct(Category.Drum, "Yamaha1 Revstar RS620 Drum", 714.0, "Nguyen", 10),
            oldProduct(Category.Organ, "Nord21 88-Key Organ", 89.99, "Ba Huy", 9),
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
            Product(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                "test"
            ),
            Product(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                "test"
            ),
            Product(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                "test"
            ),
            Product(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                "test"
            ),
            Product(
                Category.Piano,
                "Nord Piano 4 88-Key Digital Piano",
                2.999,
                "Tho Chung",
                "test"
            ),
            Product(Category.Piano, "Nord Piano 4 88-Key Digital Piano", 2.999, "Tho Chung", "test")
        )
    }
}