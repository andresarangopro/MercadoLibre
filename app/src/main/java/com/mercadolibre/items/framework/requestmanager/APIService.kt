package com.mercadolibre.items.framework.requestmanager

import com.mercadolibre.items.domain.ProductListObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MercadoLibreApi {
    companion object {
        const val ENDPOINT_PRODUCTS = "/sites/MCO/search?"
    }

    @GET(ENDPOINT_PRODUCTS)
    fun getProductsBySearch(@Query("q") q: String?, @Query("limit") limit: Int?): Call<ProductResponseServer>
}
