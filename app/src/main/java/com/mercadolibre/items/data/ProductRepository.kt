package com.mercadolibre.items.data

import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.core.platform.NetworkHandler
import com.mercadolibre.items.domain.Product
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.framework.requestmanager.*
import com.mercadolibre.items.framework.requestmanager.di.APIServiceModule
import javax.inject.Inject

interface MercadoLibreRepository {
    fun productList(search: String, limit: Int = 50):
            Either<Failure, List<ProductListObject>>

    fun productDetail(id: String):
            Either<Failure, Product>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: APIServiceModule
    ) : MercadoLibreRepository {

        override fun productList(
            search: String,
            limit: Int
        ): Either<Failure, List<ProductListObject>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getProductsBySearch(search, limit),
                    { it.toProductDomainList() },
                    ProductResponseServer(emptyList())
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun productDetail(id: String): Either<Failure, Product> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getProductDetail(id),
                    { it.toProductDomain() },
                    emptyList()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }

}