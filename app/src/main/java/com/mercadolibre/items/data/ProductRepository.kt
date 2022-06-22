package com.mercadolibre.items.data

import androidx.lifecycle.Transformations.map
import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.core.platform.NetworkHandler
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.framework.requestmanager.ProductListObjectServer
import com.mercadolibre.items.framework.requestmanager.ProductResponseServer
import com.mercadolibre.items.framework.requestmanager.di.APIServiceModule
import com.mercadolibre.items.framework.requestmanager.toProductDomainList
import javax.inject.Inject

interface MercadoLibreRepository {
    fun productList(search: String, limit: Int = 50):
            Either<Failure, List<ProductListObject>>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: APIServiceModule
    ): MercadoLibreRepository {

        override fun productList(
            search: String,
            limit: Int
        ): Either<Failure, List<ProductListObject>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getProductsBySearch(search, limit),
                    {it.toProductDomainList()},
                    ProductResponseServer(emptyList())
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }

}