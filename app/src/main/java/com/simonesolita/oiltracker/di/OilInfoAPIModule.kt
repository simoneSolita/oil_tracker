package com.simonesolita.oiltracker.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simonesolita.oiltracker.retrofit.APIConstants
import com.simonesolita.oiltracker.retrofit.LocalDateTypeAdapter
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OilInfoAPIModule {

    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter().nullSafe())
        .create()

    @Provides
    @Singleton
    fun provideAPI(builder: Retrofit.Builder): OilInfoAPI {
        return builder.build().create(OilInfoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}