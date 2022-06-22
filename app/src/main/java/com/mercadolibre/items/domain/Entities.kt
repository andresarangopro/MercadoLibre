package com.mercadolibre.items.domain

data class ProductListObject(
    val id:String?,
    val title:String,
    val seller: Seller,
    val price:Int,
    val available_quantity:Int,
    val thumbnail:String,
    val condition:String,
    val address: Address,
    val shipping: Shipping
)

data class Seller(
    val id:String
)

data class Address(
    val stateId:String,
    val stateName:String,
    val cityId:String,
    val cityName:String
)

data class Shipping(
    val freeShipping:Boolean
)