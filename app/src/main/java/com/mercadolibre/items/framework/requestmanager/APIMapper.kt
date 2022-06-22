package com.mercadolibre.items.framework.requestmanager

import com.mercadolibre.items.domain.Address
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.domain.Seller
import com.mercadolibre.items.domain.Shipping

fun ProductResponseServer.toProductDomainList():List<ProductListObject> = results.map{
    it.run{
        ProductListObject(
            id,
            title,
            seller.toDomainSeller(),
            price,
            available_quantity,
            thumbnail,
            condition,
            address.toDomainAddress(),
            shipping.toDomainShipping(),
        )
    }
}

fun SellerServer.toDomainSeller()= Seller(
    id
)

fun AddressServer.toDomainAddress()= Address(
    stateId,
    stateName,
    cityId,
    cityName
)

fun ShippingServer.toDomainShipping()= Shipping(
    freeShipping
)