package com.mercadolibre.items.presentation


import com.mercadolibre.items.core.functional.Either
import com.mercadolibre.items.data.MercadoLibreRepository
import com.mercadolibre.items.domain.Product
import com.mercadolibre.items.domain.usecases.GetDetailProductUseCase
import com.mercadolibre.items.ui.viewmodels.ProductDetailViewModel
import com.mercadolibre.items.utils.UnitTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ProductDetailViewModelTest : UnitTest() {

    private lateinit var productDetailViewModel: ProductDetailViewModel

    private lateinit var getDetailProductUseCase: GetDetailProductUseCase

    @MockK
    private lateinit var mercadoLibreRepository: MercadoLibreRepository

    @Before
    fun setUp() {
        getDetailProductUseCase = GetDetailProductUseCase(mercadoLibreRepository)
        every { mercadoLibreRepository.productDetail(mockId) } returns Either.Right(Product.empty)
        productDetailViewModel = ProductDetailViewModel(getDetailProductUseCase)
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getDetailProductUseCase.run(mockParams) }

        verify(exactly = 1) { mercadoLibreRepository.productDetail(mockId) }
    }

    @Test
    fun `loading movie details should update live data`() {

        coEvery { getDetailProductUseCase.run(mockParams) } returns Either.Right(mockProduct)

        productDetailViewModel.detailProduct.observeForever {
            with(it!!) {
                id shouldBeEqualTo "1"
                title shouldBeEqualTo "Bicicleta"
                price shouldBeEqualTo 1234564
                stringPrice shouldBeEqualTo "$ 123.456"
                sold shouldBeEqualTo 5
                condition shouldBeEqualTo "new"
            }
        }
    }

    companion object {
        private const val mockId = "1"
        private val mockParams = GetDetailProductUseCase.Params(mockId)
        private val mockProduct = Product(
            "1",
            "Bicicleta",
            123456,
            "$ 123.465",
            5,
            "new",
            emptyList()
        )
    }
}
