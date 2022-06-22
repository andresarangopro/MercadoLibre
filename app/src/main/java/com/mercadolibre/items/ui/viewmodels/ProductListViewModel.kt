package com.mercadolibre.items.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.items.core.platform.BaseViewModel
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase
import com.mercadolibre.items.domain.usecases.GetListProductsBySearchUseCase.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel
@Inject constructor(
    private val getListProductsBySearchUseCase: GetListProductsBySearchUseCase
): BaseViewModel<ProductListViewModel.EventsProductListViewModel,
        ProductListViewModel.StatesProductListViewModel>(){

    private val _listProducts: MutableLiveData<List<ProductListObject>> = MutableLiveData()
    val listProducts: LiveData<List<ProductListObject>> = _listProducts

    private val _states: MutableLiveData<States<StatesProductListViewModel>> = MutableLiveData()
    val states: LiveData<States<StatesProductListViewModel>> get() =  _states

    private fun loadListProducts(search: String, limit: Int) =
        getListProductsBySearchUseCase(Params(search, limit), viewModelScope) {
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

    sealed class EventsProductListViewModel{
        data class WritedWord(val search: String):EventsProductListViewModel()
    }

    sealed class StatesProductListViewModel{
        data class LoadAdapterListProducts(val listProducts: List<ProductListObject>):StatesProductListViewModel()
    }

    override fun manageEvent(event: EventsProductListViewModel) {
        when(event){
            is EventsProductListViewModel.WritedWord ->{
                loadListProducts(event.search, 50)
            }
        }
    }
}