package com.mercadolibre.items.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.items.core.platform.BaseViewModel
import com.mercadolibre.items.domain.Product
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.domain.usecases.GetDetailProductUseCase
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel
@Inject constructor(
    private val getDetailProductUseCase: GetDetailProductUseCase
) : BaseViewModel<ProductDetailViewModel.EventsProductListViewModel,
        ProductDetailViewModel.StatesProductListViewModel>() {


    private val _detailProduct: MutableLiveData<Product> = MutableLiveData()
    val detailProduct: LiveData<Product> get() = _detailProduct

    private val _states: MutableLiveData<States<StatesProductListViewModel>> = MutableLiveData()
    val states: LiveData<States<StatesProductListViewModel>> get() = _states


    private fun getDetailProduct(id: String) =
        getDetailProductUseCase(GetDetailProductUseCase.Params(id), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleDetailProduct
            )
        }

    private fun handleDetailProduct(product: Product) {
        _detailProduct.value = product
        detailProduct.value?.let {
            _states.value = States(StatesProductListViewModel.LoadDetailProduct(it))
        }

    }

    sealed class EventsProductListViewModel {
        data class GetDetailProduct(val id: String) : EventsProductListViewModel()
        object ReloadAdapterIfListIsDifferentOfNull : EventsProductListViewModel()
    }

    sealed class StatesProductListViewModel {
        data class LoadDetailProduct(val product: Product) : StatesProductListViewModel()
    }

    override fun manageEvent(event: EventsProductListViewModel) {
        when (event) {
            is EventsProductListViewModel.GetDetailProduct ->{
                getDetailProduct(event.id)
            }
            else -> {}
        }
    }
}