package com.pk.assignment.domain.model

data class Thumbnail(
    val aspectRatio: Float,
    val basePath: String,
    val domain: String,
    val id: String,
    val key: String,
    val qualities: List<Int>,
    val version: Int
)