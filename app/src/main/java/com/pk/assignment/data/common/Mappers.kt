package com.pk.assignment.data.common

import com.pk.assignment.data.model.BackupDetailsDTA
import com.pk.assignment.data.model.RecordItemDTO
import com.pk.assignment.data.model.ThumbnailDTO
import com.pk.assignment.domain.model.BackupDetails
import com.pk.assignment.domain.model.RecordItem
import com.pk.assignment.domain.model.Thumbnail

fun RecordItemDTO.toDomain(): RecordItem {
    return RecordItem(
        backupDetails = backupDetails?.toDomain() ?: BackupDetails(),
        coverageURL = coverageURL,
        id = id,
        language = language,
        mediaType = mediaType,
        publishedAt = publishedAt,
        publishedBy = publishedBy,
        thumbnail = thumbnail.toDomain(),
        title = title
    )
}

fun BackupDetailsDTA.toDomain(): BackupDetails {
    return BackupDetails(
        pdfLink = pdfLink,
        screenshotURL = screenshotURL,
    )
}

fun ThumbnailDTO.toDomain(): Thumbnail {
    return Thumbnail(
        aspectRatio = aspectRatio,
        basePath = basePath,
        domain = domain,
        id = id,
        key = key,
        qualities = qualities,
        version = version
    )
}

