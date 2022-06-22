package com.mercadolibre.items.data

import com.mercadolibre.items.core.exception.Failure.FeatureFailure

class ProductFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentProducts : FeatureFailure()
}