package com.mercadolibre.items.domain.usecases

import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.core.interactor.UseCase
import com.mercadolibre.items.data.MercadoLibreRepository
import com.mercadolibre.items.domain.ProductListObject
import javax.inject.Inject
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase.Params

class GetListProductsBySearchUseCase
@Inject constructor(private val mercadoLibreRepository: MercadoLibreRepository) : UseCase<List<ProductListObject>, Params>(){
    override suspend fun run(params: Params): Either<Failure, List<ProductListObject>> {
        var mercadoLibreRepository = mercadoLibreRepository.productList(params.search, params.limit)

        return mercadoLibreRepository
    }

    data class Params(val search: String, val limit: Int)
}