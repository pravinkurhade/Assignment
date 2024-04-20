package com.pk.packman.cache

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache : ImageCache {

    private var memoryCache: LruCache<String, Bitmap>

    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/8th of the available memory for this memory cache.
    val cacheSize = maxMemory / 2

    init {
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    override fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        return memoryCache.get(url)
    }

    override fun has(url: String): Boolean {
       return memoryCache.snapshot().containsKey(url)
    }

    override fun clear() {
        memoryCache.evictAll()
    }
}