package com.pk.packman.cache

import android.graphics.Bitmap

interface ImageCache {
    fun put(url: String, bitmap: Bitmap)
    fun get(url: String): Bitmap?
    fun has(url: String): Boolean?
    fun clear()
}