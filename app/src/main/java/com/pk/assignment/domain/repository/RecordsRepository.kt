package com.pk.assignment.domain.repository

import com.pk.assignment.domain.model.RecordItem

interface RecordsRepository {
    suspend fun getRecords() : List<RecordItem>
}