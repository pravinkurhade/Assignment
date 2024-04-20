package com.pk.assignment.data.network

import com.pk.assignment.data.model.RecordItemDTO
import retrofit2.http.GET

interface ApiService {

    @GET("api/v2/content/misc/media-coverages?limit=300")
    suspend fun getRecords() : List<RecordItemDTO>
}