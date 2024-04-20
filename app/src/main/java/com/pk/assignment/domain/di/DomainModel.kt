package com.pk.assignment.domain.di

import com.pk.assignment.domain.repository.RecordsRepository
import com.pk.assignment.domain.use_cases.GetRecordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModel {

    @Provides
    fun provideGetRecordsUseCase(recordsRepository: RecordsRepository): GetRecordsUseCase {
        return GetRecordsUseCase(recordsRepository)
    }

}