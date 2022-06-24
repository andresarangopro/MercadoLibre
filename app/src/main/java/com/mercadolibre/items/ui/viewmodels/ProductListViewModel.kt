package com.mercadolibre.items.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.items.core.platform.BaseViewModel
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _queryWord: MutableLiveData<String> = MutableLiveData()
    private val queryWord: LiveData<String> get() = _queryWord

    private var isRequestInProgress = false

    private fun loadListProducts(limit: Int) =
        getListProductsBySearchUseCase(
            GetListProductsBySearchUseCase.Params(queryWord.value, limit),
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
        _states.value = States(StatesProductListViewModel.HideBottomLoader)
    }

    sealed class EventsProductListViewModel {
        data class GetListBySearch(val search: String, val limit: Int) :
            EventsProductListViewModel()

        object ReloadAdapterIfListIsDifferentOfNull : EventsProductListViewModel()


    }

    sealed class StatesProductListViewModel {
        data class LoadAdapterListProducts(val listProducts: List<ProductListObject>) :
            StatesProductListViewModel()

        object ShowBottomLoader : StatesProductListViewModel()

        object HideBottomLoader : StatesProductListViewModel()
    }

    override fun manageEvent(event: EventsProductListViewModel) {
        when (event) {
            is EventsProductListViewModel.GetListBySearch -> {
                _queryWord.value = event.search
                loadListProducts(event.limit)
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

    fun listScrolled(lastVisibleItem: Int, totalItems: Int) {
        if (totalItems - 1 - lastVisibleItem == 0 && isRequestInProgress.not() && totalItems < 50) {
            _states.value = States(StatesProductListViewModel.ShowBottomLoader)
            isRequestInProgress = true
            loadListProducts(totalItems + 10)
            isRequestInProgress = false
        }
    }
}