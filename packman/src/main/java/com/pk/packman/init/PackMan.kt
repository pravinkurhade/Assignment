package com.pk.packman.init

import android.app.Application
import android.graphics.Bitmap
import com.pk.packman.cache.DiskCache
import com.pk.packman.cache.ImageCache
import com.pk.packman.cache.MemoryCache

class PackMan(application: Application) : ImageCache {

    private val memoryCache = MemoryCache()
    private val diskCache = DiskCache(application)

    override fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
        diskCache.put(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        val imageMemoryCache = memoryCache.get(url)
        return if (imageMemoryCache != null){
            imageMemoryCache
        }else {
            val imageDiskCache = diskCache.get(url)
            if (imageDiskCache != null){
                memoryCache.put(url,imageDiskCache)
                imageDiskCache
            }else{
                null
            }
        }
    }

    override fun has(url: String): Boolean {
        return memoryCache.has(url)
    }

    override fun clear() {
        memoryCache.clear()
        diskCache.clear()
    }
}