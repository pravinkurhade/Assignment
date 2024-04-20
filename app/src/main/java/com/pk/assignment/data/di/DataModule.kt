package com.pk.assignment.data.di

import com.pk.assignment.data.network.ApiService
import com.pk.assignment.data.repository.RecordsRepoImpl
import com.pk.assignment.domain.repository.RecordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAPIService(): ApiService{
        return Retrofit.Builder().baseUrl("https://acharyaprashant.org/").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRecordsRepo(apiService: ApiService) : RecordsRepository{
        return RecordsRepoImpl(apiService)
    }


}