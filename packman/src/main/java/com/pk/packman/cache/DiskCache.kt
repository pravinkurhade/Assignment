package com.pk.packman.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.Environment.isExternalStorageRemovable
import com.pk.packman.jakewharton.DiskLruCache
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DiskCache(private val context: Context) : ImageCache {

    private val DISK_CACHE_SUBDIR = "thumbnails"
    private var cache: DiskLruCache =
        DiskLruCache.open(getDiskCacheDir(context, DISK_CACHE_SUBDIR), 1, 1, 100 * 1024 * 1024)

    override fun get(url: String): Bitmap? {
        val key = md5(url)
        val snapshot: DiskLruCache.Snapshot? = cache.get(key)
        return if (snapshot != null) {
            val inputStream: InputStream = snapshot.getInputStream(0)
            val buffIn = BufferedInputStream(inputStream, 8 * 1024)
            BitmapFactory.decodeStream(buffIn)
        } else {
            null
        }
    }

    override fun has(url: String): Boolean? {
        val key = md5(url)
        val snapshot: DiskLruCache.Snapshot? = cache.get(key)
        return snapshot != null
    }

    override fun put(url: String, bitmap: Bitmap) {
        val key = md5(url)
        var editor: DiskLruCache.Editor? = null
        try {
            editor = cache.edit(key)
            if (editor == null) {
                return
            }
            if (writeBitmapToFile(bitmap, editor)) {
                cache.flush()
                editor.commit()
            } else {
                editor.abort()
            }
        } catch (e: IOException) {
            try {
                editor?.abort()
            } catch (ignored: IOException) {
            }
        }
    }

    override fun clear() {
        cache.delete()
        cache = DiskLruCache.open(
            context.cacheDir, 1, 1,
            10 * 1024 * 1024
        )
    }

    private fun writeBitmapToFile(
        bitmap: Bitmap, editor:
        DiskLruCache.Editor
    ): Boolean {
        var out: OutputStream? = null
        try {
            out = BufferedOutputStream(editor.newOutputStream(0), 8 * 1024)
            return bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } finally {
            out?.close()
        }
    }

    fun md5(url: String): String? {
        try {

            // Static getInstance method is called with hashing MD5
            val md = MessageDigest.getInstance("MD5")

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            val messageDigest = md.digest(url.toByteArray())

            // Convert byte array into signum representation
            val no = BigInteger(1, messageDigest)

            // Convert message digest into hex value
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
        // For specifying wrong message digest algorithms
    }

    companion object {
        private val INSTANCE: DiskCache? = null

        @Synchronized
        fun getInstance(context: Context): DiskCache {
            return INSTANCE?.let { return INSTANCE }
                ?: run {
                    return DiskCache(context)
                }
        }
    }


    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
    // but if not mounted, falls back on internal storage.
    private fun getDiskCacheDir(context: Context, uniqueName: String): File {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        val cachePath =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !isExternalStorageRemovable()
            ) {
                context.externalCacheDir?.path
            } else {
                context.cacheDir.path
            }

        return File(cachePath + File.separator + uniqueName)
    }
}