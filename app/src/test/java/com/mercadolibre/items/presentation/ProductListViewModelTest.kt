package com.mercadolibre.items.presentation

import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.data.MercadoLibreRepository
import com.mercadolibre.items.domain.*
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase
import com.mercadolibre.items.presentation.MockDataProductList.mockLimit
import com.mercadolibre.items.presentation.MockDataProductList.mockParams
import com.mercadolibre.items.presentation.MockDataProductList.mockProductListObject
import com.mercadolibre.items.presentation.MockDataProductList.mockSearch
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel
import com.mercadolibre.items.utils.UnitTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ProductListViewModelTest : UnitTest() {

    private lateinit var productListViewModel: ProductListViewModel

    private lateinit var getListProductsBySearchUseCase: GetListProductsBySearchUseCase

    @MockK
    private lateinit var mercadoLibreRepository: MercadoLibreRepository

    @Before
    fun setUp() {
        getListProductsBySearchUseCase = GetListProductsBySearchUseCase(mercadoLibreRepository)
        every { mercadoLibreRepository.productList(mockSearch, mockLimit) } returns Either.Right(
            listOf(ProductListObject.empty)
        )
        productListViewModel = ProductListViewModel(getListProductsBySearchUseCase)
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getListProductsBySearchUseCase.run(mockParams) }

        verify(exactly = 1) { mercadoLibreRepository.productList(mockSearch, mockLimit) }
    }

    @Test
    fun `loading product list should update live data`() {
        coEvery { getListProductsBySearchUseCase.run(mockParams) } returns Either.Right(
            mockProductListObject
        )

        productListViewModel.listProducts.observeForever {
            it.size shouldBeEqualTo 1
            it[0].id shouldBeEqualTo "1"
            it[0].title shouldBeEqualTo "Bicicleta"
            it[0].price shouldBeEqualTo 123456
            it[0].availableQuantity shouldBeEqualTo 5
            it[0].condition shouldBeEqualTo "new"
        }
        runBlocking {
            productListViewModel.postEvent(
                ProductListViewModel.EventsProductListViewModel.GetListBySearch(
                    mockSearch, mockLimit
                )
            )
        }
    }
}

object MockDataProductList {
    val mockSearch = "Bicicleta"
    val mockLimit = 10
    val mockParams = GetListProductsBySearchUseCase.Params(mockSearch, mockLimit)
    val mockProductListObject = listOf(
        ProductListObject(
            "1",
            "Bicicleta",
            Seller.empty,
            123456,
            "$ 123.456",
            5,
            "",
            "new",
            Installment.empty,
            Address.empty,
            Shipping.empty
        )
    )

}
