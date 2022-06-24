package com.mercadolibre.items.domain

data class ProductListObject(
    val id: String?,
    val title: String,
    val seller: Seller,
    val price: Long,
    val priceString: String,
    val availableQuantity: Int,
    val thumbnail: String,
    val condition: String,
    val installment: Installment?,
    val address: Address,
    val shipping: Shipping
){
    companion object {
        val empty = ProductListObject(
            "",
            "",
            Seller.empty,
            0,
            "",
            0,
            "",
            "",
            Installment.empty,
            Address.empty,
            Shipping.empty
        )
    }
}

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

    companion object {
        val empty = Product(
            "",
            "",
            0,
            "",
            0,
            "",
            emptyList()
        )
    }
}

data class Picture(
    val id: String,
    val url: String
)

data class Seller(
    val id: String
){
    companion object {
        val empty = Seller(
            ""
        )
    }
}

data class Installment(
    val quantity: Int,
    val amount: Double,
    val amountString: String,
    val rate: Float
){
    companion object {
        val empty = Installment(
            0,
            0.0,
            "",
            0f
        )
    }
}

data class Address(
    val stateId: String,
    val stateName: String,
    val cityId: String,
    val cityName: String
){
    companion object {
        val empty = Address(
            "",
            "",
            "",
            ""
        )
    }
}

data class Shipping(
    val freeShipping: Boolean
){
    companion object {
        val empty = Shipping(false)
    }
}