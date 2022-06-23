package com.mercadolibre.items.domain.usecases

import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.core.interactor.UseCase
import com.mercadolibre.items.data.MercadoLibreRepository
import com.mercadolibre.items.domain.Product
import javax.inject.Inject
import com.mercadolibre.items.domain.usecases.GetDetailProductUseCase.Params

class GetDetailProductUseCase
@Inject constructor(private val mercadoLibreRepository: MercadoLibreRepository) : UseCase<Product, Params>(){

    override suspend fun run(params: Params): Either<Failure, Product> {
        return mercadoLibreRepository.productDetail(params.id)
    }

    data class Params(val id: String)
}