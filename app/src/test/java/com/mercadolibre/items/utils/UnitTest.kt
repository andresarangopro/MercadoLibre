package com.mercadolibre.items.utils


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class UnitTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField val injectMocks = InjectMocksRule.create(this@UnitTest)

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    fun fail(message: String): Nothing = throw AssertionError(message)
}
