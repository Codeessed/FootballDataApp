package com.example.footballdataapp.di

import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.repository.network.NetworkDataImpl
import com.example.footballdataapp.repository.network.NetworkDataRepository
import com.example.footballdataapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofitInstance(baseUrl: String): Retrofit {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = OkHttpClient.Builder().addInterceptor(logger).build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
//            .client(interceptor)

        return retrofit.build()
    }

    @Provides
    @Singleton
    fun provideModuleApi(): ApiInterface {
        return providesRetrofitInstance(BASE_URL).create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesCardRepository(apiInterface: ApiInterface): NetworkDataRepository =
        NetworkDataImpl(apiInterface)
}