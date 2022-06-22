package com.mercadolibre.items.framework.requestmanager


import com.mercadolibre.items.BuildConfig
import com.mercadolibre.items.framework.requestmanager.APIConstants.BASE_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object MercadoLibreModule {

    /* @Singleton
    @Provides
    fun mercadoLibreAPI(retrofit: Retrofit): MercadoLibreApi =
        retrofit.create(MercadoLibreApi::class.java)*/

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}