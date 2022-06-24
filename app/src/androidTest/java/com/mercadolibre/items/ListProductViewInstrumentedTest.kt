package com.mercadolibre.items

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.rule.BaristaRule
import com.mercadolibre.items.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ListProductViewInstrumentedTest : BaseUITest() {

    @get:Rule
    var baristaRule: BaristaRule<MainActivity> = BaristaRule.create(MainActivity::class.java)

    companion object {
        const val mockSearch = "Bicicleta"
    }

    @Test
    fun showResultsOnSearchOfProductSuccess() {

        baristaRule.launchActivity()

        onView(withId(R.id.searchProduct))
            .perform(
                typeText(mockSearch), pressKey(
                    KeyEvent.KEYCODE_ENTER
                )
            )

        Thread.sleep(2000)

        onView(
            CoreMatchers.allOf(
                withId(R.id.product_name),
                isDescendantOfA(nthChildOf(withId(R.id.rvPlaceList), 0))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun showDetailScreenWhenClickSource() {
        baristaRule.launchActivity()

        onView(withId(R.id.searchProduct))
            .perform(
                typeText(mockSearch), pressKey(
                    KeyEvent.KEYCODE_ENTER
                )
            )

        Thread.sleep(2000)

        onView(withId(R.id.rvPlaceList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        assertDisplayed(R.id.product_title)
    }

    @Test
    fun goBackWhenTabBackButton() {
        baristaRule.launchActivity()

        onView(withId(R.id.searchProduct))
            .perform(
                typeText(mockSearch), pressKey(
                    KeyEvent.KEYCODE_ENTER
                )
            )

        Thread.sleep(2000)

        onView(withId(R.id.rvPlaceList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.backArrow))
            .perform(click())

        Thread.sleep(1000)

        onView(
            CoreMatchers.allOf(
                withId(R.id.product_name),
                isDescendantOfA(nthChildOf(withId(R.id.rvPlaceList), 0))
            )
        ).check(matches(isDisplayed()))

    }

    @Test
    fun showAndHideProgressBarWhenIsLoading() {
        baristaRule.launchActivity()

        onView(withId(R.id.searchProduct))
            .perform(
                typeText(mockSearch), pressKey(
                    KeyEvent.KEYCODE_ENTER
                )
            )

        assertDisplayed(R.id.progress_circular)

        Thread.sleep(2000)

        assertNotDisplayed(R.id.progress_circular)
    }


}