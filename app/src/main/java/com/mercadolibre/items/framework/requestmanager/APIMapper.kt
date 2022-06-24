package com.mercadolibre.items.framework.requestmanager

import com.mercadolibre.items.domain.*

fun ProductResponseServer.toProductDomainList(): List<ProductListObject> = results.map {
    it.run {
        ProductListObject(
            id,
            title,
            seller.toDomainSeller(),
            price,
            price.toFormattedNumber(),
            available_quantity,
            thumbnail,
            condition,
            installment?.toDomainInstallments(),
            address.toDomainAddress(),
            shipping.toDomainShipping(),
        )
    }
}


fun List<ProductBodyServer>.toProductDomain(): Product = Product(
    this[0].body.id,
    this[0].body.title,
    this[0].body.price,
    this[0].body.price.toFormattedNumber(),
    this[0].body.sold,
    this[0].body.condition,
    this[0].body.pictures.toDomainProductPictures()
)

fun List<ProductPictureServer>.toDomainProductPictures() = this.map {
    it.run {
        Picture(
            id,
            url
        )
    }
}

fun SellerServer.toDomainSeller() = Seller(
    id
)

fun InstallmentsServer.toDomainInstallments() = Installment(
    quantity,
    amount,
    "$quantity x ${amount.toFormattedNumber()}",
    rate
)

fun AddressServer.toDomainAddress() = Address(
    stateId,
    stateName,
    cityId,
    cityName
)

fun ShippingServer.toDomainShipping() = Shipping(
    freeShipping
)

fun Long.toFormattedNumber() = "$ ${String.format("%,d", this)}"

fun Double.toFormattedNumber() = "$ ${String.format("%,d", this.toLong())}"