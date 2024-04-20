package com.pk.assignment.data.model

data class RecordItemDTO(
    val backupDetails: BackupDetailsDTA? = null,
    val coverageURL: String,
    val id: String,
    val language: String,
    val mediaType: Int,
    val publishedAt: String,
    val publishedBy: String,
    val thumbnail: ThumbnailDTO,
    val title: String
)