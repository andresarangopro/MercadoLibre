package com.mercadolibre.items.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.items.core.exception.Failure

abstract class BaseViewModel<in Event, State>  : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    private val _screenState: MutableLiveData<State?> = MutableLiveData()
    val screenState: MutableLiveData<State?>
        get() = _screenState

    fun postEvent(event: Event) {
        manageEvent(event)
    }

    protected abstract fun manageEvent(event: Event)
}