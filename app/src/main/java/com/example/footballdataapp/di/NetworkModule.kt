package com.example.footballdataapp.di

import android.content.Context
import androidx.room.Room
import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.db.dao.CompetitionDao
import com.example.footballdataapp.db.database.DataDatabase
//import com.example.footballdataapp.repository.db.DatabaseDataImpl
//import com.example.footballdataapp.repository.db.DatabaseDataRepository
//import com.example.footballdataapp.repository.network.NetworkDataImpl
//import com.example.footballdataapp.repository.network.NetworkDataRepository
import com.example.footballdataapp.util.Constants.BASE_URL
import com.example.footballdataapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofitInstance(baseUrl: String): Retrofit {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = OkHttpClient.Builder().addInterceptor(logger).build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(interceptor)

        return retrofit.build()
    }

    @Singleton
    @Provides
    fun provideDatabase(
       @ApplicationContext context: Context
    ): DataDatabase = Room.databaseBuilder(
        context,
        DataDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDataDao(db: DataDatabase) = db.getCompetitionDao()

    @Singleton
    @Provides
    fun provideModuleApi(): ApiInterface {
        return providesRetrofitInstance(BASE_URL).create(ApiInterface::class.java)
    }

//    @Singleton
//    @Provides
//    fun providesNetworkRepository(apiInterface: ApiInterface): NetworkDataRepository =
//        NetworkDataImpl(apiInterface)
//
//    @Singleton
//    @Provides
//    fun providesDatabaseRepository(competitionDao: CompetitionDao): DatabaseDataRepository =
//        DatabaseDataImpl(competitionDao)
}