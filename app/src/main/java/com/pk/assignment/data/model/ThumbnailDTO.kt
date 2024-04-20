package com.pk.assignment.data.model

data class ThumbnailDTO(
    val aspectRatio: Float,
    val basePath: String,
    val domain: String,
    val id: String,
    val key: String,
    val qualities: List<Int>,
    val version: Int
)