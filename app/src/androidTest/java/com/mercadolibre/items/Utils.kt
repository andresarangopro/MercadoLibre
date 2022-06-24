package com.mercadolibre.items


import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom


fun getText(matcher: ViewInteraction): String {
    var text = String()
    matcher.perform(object : ViewAction {
        override fun getConstraints(): org.hamcrest.Matcher<View>? {
            return isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            val tv = view as TextView
            text = tv.text.toString()
        }
    })

    return text
}