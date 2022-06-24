package com.mercadolibre.items.core.extensions

import android.content.Context
import android.view.View
import com.mercadolibre.items.R
import com.mercadolibre.items.core.platform.BaseFragment
import com.mercadolibre.items.ui.MainActivity

val BaseFragment.viewContainer: View? get() = activity?.findViewById(R.id.nav_host_fragment_content_main)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!