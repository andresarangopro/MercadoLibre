package com.mercadolibre.items.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import java.text.DecimalFormat
import java.text.NumberFormat

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
