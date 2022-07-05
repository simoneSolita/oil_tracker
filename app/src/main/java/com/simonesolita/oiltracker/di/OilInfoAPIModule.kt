package com.simonesolita.oiltracker.di

import com.simonesolita.oiltracker.retrofit.APIConstants
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OilInfoAPIModule {

    @Provides
    @Singleton
    fun provideAPI(builder: Retrofit.Builder): OilInfoAPI {
        return builder.build().create(OilInfoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }
}