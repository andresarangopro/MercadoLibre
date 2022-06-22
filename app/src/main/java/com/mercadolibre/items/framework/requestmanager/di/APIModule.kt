package com.mercadolibre.items.framework.requestmanager.di

import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.framework.requestmanager.MercadoLibreApi
import com.mercadolibre.items.framework.requestmanager.ProductResponseServer
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIServiceModule
@Inject constructor(retrofit: Retrofit) : MercadoLibreApi {
    private val mercadoLibreApi by lazy { retrofit.create(MercadoLibreApi::class.java) }

    override fun getProductsBySearch(query: String?, limit: Int?): Call<ProductResponseServer>
                        = mercadoLibreApi.getProductsBySearch(query,limit )


}

