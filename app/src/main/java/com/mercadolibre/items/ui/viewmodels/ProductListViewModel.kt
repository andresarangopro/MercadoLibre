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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel
@Inject constructor(
    private val getListProductsBySearchUseCase: GetListProductsBySearchUseCase
) : BaseViewModel<ProductListViewModel.EventsProductListViewModel,
        ProductListViewModel.StatesProductListViewModel>() {

    private val _listProducts: MutableLiveData<List<ProductListObject>> = MutableLiveData()
    val listProducts: LiveData<List<ProductListObject>> get() = _listProducts

    private val _states: MutableLiveData<States<StatesProductListViewModel>> = MutableLiveData()
    val states: LiveData<States<StatesProductListViewModel>> get() = _states

    private var isRequestInProgress = false

    private fun loadListProducts(search: String, limit: Int) =
        getListProductsBySearchUseCase(
            GetListProductsBySearchUseCase.Params(search, limit),
            viewModelScope
        ) {
            it.fold(
                ::handleFailure,
                ::handleListProducts
            )
        }

    private fun handleListProducts(productList: List<ProductListObject>) {
        _listProducts.value = productList
        listProducts.value?.let {
            _states.value = States(StatesProductListViewModel.LoadAdapterListProducts(it))
        }
    }

    sealed class EventsProductListViewModel {
        data class WroteWord(val search: String) : EventsProductListViewModel()
        object ReloadAdapterIfListIsDifferentOfNull : EventsProductListViewModel()
    }

    sealed class StatesProductListViewModel {
        data class LoadAdapterListProducts(val listProducts: List<ProductListObject>) :
            StatesProductListViewModel()
    }

    override fun manageEvent(event: EventsProductListViewModel) {
        when (event) {
            is EventsProductListViewModel.WroteWord -> {
                loadListProducts(event.search, 50)
            }
            is EventsProductListViewModel.ReloadAdapterIfListIsDifferentOfNull -> {
                if (listProducts.value?.isNotEmpty() == true) {
                    listProducts.value?.let {
                        _states.value =
                            States(StatesProductListViewModel.LoadAdapterListProducts(it))
                    }
                }
            }
        }
    }

    fun listScrolled(search: String, lastVisibleItem: Int, totalItems: Int) {
        if (totalItems - 1 - lastVisibleItem == 0) {
            if (isRequestInProgress.not()) {
                isRequestInProgress = true
                loadListProducts(search, totalItems)
                isRequestInProgress = false
            }
        }
    }
}