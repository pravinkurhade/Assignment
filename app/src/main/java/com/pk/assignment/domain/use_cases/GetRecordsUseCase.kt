package com.pk.assignment.domain.use_cases

import com.pk.assignment.data.common.Resources
import com.pk.assignment.domain.model.RecordItem
import com.pk.assignment.domain.repository.RecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class GetRecordsUseCase(private val recordsRepository: RecordsRepository) {
    operator fun invoke(): Flow<Resources<List<RecordItem>>> = flow {
        emit(Resources.Loading())
        try {
            emit(Resources.Success(data = recordsRepository.getRecords()))
        } catch (e: Exception) {
            emit(Resources.Error(message = e.message))
        }
    }
}