package com.pk.assignment.data.repository

import com.pk.assignment.data.common.toDomain
import com.pk.assignment.data.network.ApiService
import com.pk.assignment.domain.model.RecordItem
import com.pk.assignment.domain.repository.RecordsRepository

class RecordsRepoImpl(private val apiService: ApiService) : RecordsRepository {
    override suspend fun getRecords(): List<RecordItem> {
        return apiService.getRecords().map { it.toDomain() }
    }
}