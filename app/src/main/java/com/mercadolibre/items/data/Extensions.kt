package com.mercadolibre.items.data

import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.functional.Either
import retrofit2.Call

fun <T, R> request(
    call: Call<T>,
    transform: (T) -> R,
    default: T
): Either<Failure, R> {
    return try {
        Either.Left(Failure.ServerError)
        val response = call.execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform((response.body() ?: default)))
            false -> Either.Left(Failure.ServerError)
        }
    } catch (exception: Throwable) {
        Either.Left(Failure.ServerError)
    }
}
