package com.mercadolibre.items.framework.requestmanager

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_ADDRESS
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_AMOUNT
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_AVAILABLE_QUANTITY
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_BODY
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_CITY_ID
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_CITY_NAME
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_CODE
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_CONDITION
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_FREE_SHIPPING8
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_ID
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_INSTALLMENTS
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_PICTURES
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_PRICE
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_QUANTITY
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_RATE
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_RESULTS
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_SELLER
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_SHIPPING
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_SOLD
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_STATE_ID
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_STATE_NAME
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_THUMBNAIL
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_TITLE
import com.mercadolibre.items.framework.requestmanager.APIConstants.KEY_URL
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductListObjectServer(
    @SerializedName(KEY_ID) val id: String?,
    @SerializedName(KEY_TITLE) val title: String,
    @SerializedName(KEY_SELLER) val seller: SellerServer,
    @SerializedName(KEY_PRICE) val price: Long,
    @SerializedName(KEY_AVAILABLE_QUANTITY) val available_quantity: Int,
    @SerializedName(KEY_THUMBNAIL) val thumbnail: String,
    @SerializedName(KEY_CONDITION) val condition: String,
    @SerializedName(KEY_ADDRESS) val address: AddressServer,
    @SerializedName(KEY_INSTALLMENTS) val installment: InstallmentsServer?,
    @SerializedName(KEY_SHIPPING) val shipping: ShippingServer
) : Parcelable

@Parcelize
data class ProductBodyServer(
    @SerializedName(KEY_CODE) val code: Int,
    @SerializedName(KEY_BODY) val body: ProductDetailServer,

) : Parcelable

@Parcelize
data class ProductDetailServer(
    @SerializedName(KEY_ID) val id: String,
    @SerializedName(KEY_TITLE) val title: String,
    @SerializedName(KEY_PRICE) val price: Long,
    @SerializedName(KEY_SOLD) val sold: Int,
    @SerializedName(KEY_CONDITION) val condition: String,
    @SerializedName(KEY_PICTURES) val pictures: List<ProductPictureServer>

) : Parcelable

@Parcelize
data class ProductPictureServer(
    @SerializedName(KEY_ID) val id: String,
    @SerializedName(KEY_URL) val url: String
) : Parcelable

@Parcelize
data class ProductResponseServer(
    @SerializedName(KEY_RESULTS)
    val results: List<ProductListObjectServer>
) : Parcelable

@Parcelize
data class AddressServer(
    @SerializedName(KEY_STATE_ID) val stateId: String,
    @SerializedName(KEY_STATE_NAME) val stateName: String,
    @SerializedName(KEY_CITY_ID) val cityId: String,
    @SerializedName(KEY_CITY_NAME) val cityName: String,
) : Parcelable

@Parcelize
data class InstallmentsServer(
    @SerializedName(KEY_QUANTITY) val quantity: Int,
    @SerializedName(KEY_AMOUNT) val amount: Double,
    @SerializedName(KEY_RATE) val rate: Float
) : Parcelable

@Parcelize
data class SellerServer(
    @SerializedName(KEY_ID) val id: String,
) : Parcelable

@Parcelize
data class ShippingServer(
    @SerializedName(KEY_FREE_SHIPPING8) val freeShipping: Boolean,
) : Parcelable