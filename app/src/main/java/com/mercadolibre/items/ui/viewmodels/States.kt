package com.mercadolibre.items.ui.viewmodels

data class States<out T>(private val content:T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled():T?= if(hasBeenHandled){
        null
    }else{
        hasBeenHandled = true
        content
    }
}