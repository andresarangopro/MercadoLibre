package com.mercadolibre.items.domain

data class ProductListObject(
    val id: String?,
    val title: String,
    val seller: Seller,
    val price: Long,
    val priceString: String,
    val available_quantity: Int,
    val thumbnail: String,
    val condition: String,
    val installment: Installment,
    val address: Address,
    val shipping: Shipping
)

data class Product(
    val id: String,
    val title: String,
    val price: Long,
    val stringPrice: String,
    val sold: Int,
    val condition: String,
    val pictures: List<Picture> = emptyList()
) {
    fun getSoldAndConditionString() = "$condition | $sold vendidos"
}

data class Picture(
    val id: String,
    val url: String
)

data class Seller(
    val id: String
)

data class Installment(
    val quantity: Int,
    val amount: Double,
    val amountString: String,
    val rate: Float
)

data class Address(
    val stateId: String,
    val stateName: String,
    val cityId: String,
    val cityName: String
)

data class Shipping(
    val freeShipping: Boolean
)