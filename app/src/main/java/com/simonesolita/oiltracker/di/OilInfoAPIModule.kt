package com.simonesolita.oiltracker.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simonesolita.oiltracker.dao.OilInfoDao
import com.simonesolita.oiltracker.db.AppDatabase
import com.simonesolita.oiltracker.retrofit.APIConstants
import com.simonesolita.oiltracker.retrofit.DateTypeAdapter
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        .registerTypeAdapter(LocalDate::class.java, DateTypeAdapter().nullSafe())
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

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getAppDbInstance(context)
    }

    @Provides
    @Singleton
    fun getOilInfoDao(appDatabase: AppDatabase):OilInfoDao{
        return appDatabase.getOilInfoDao()
    }
}