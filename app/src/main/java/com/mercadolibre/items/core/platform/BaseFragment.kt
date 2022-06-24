package com.mercadolibre.items.core.platform

import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mercadolibre.items.R
import com.mercadolibre.items.core.extensions.appContext
import com.mercadolibre.items.R.color
import com.mercadolibre.items.core.extensions.viewContainer
import com.mercadolibre.items.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this is MainActivity) this.findViewById<ProgressBar>(R.id.progress_circular).visibility =
                viewStatus
        }

    internal fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        action: () -> Any
    ) {
        val snackBar = viewContainer?.let { Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE) }
        snackBar?.setAction(actionText) { _ -> action.invoke() }
        snackBar?.setActionTextColor(ContextCompat.getColor(appContext, color.primary))
        snackBar?.show()
    }
}