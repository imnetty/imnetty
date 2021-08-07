package org.launch.Utils

import com.google.common.util.concurrent.RateLimiter
import java.io.IOException
import java.io.InputStream

class ThrottlingInputStream(private val target: InputStream, private val maxBytesPerSecond: RateLimiter) :
    InputStream() {
    @Throws(IOException::class)
    override fun read(): Int {
        maxBytesPerSecond.acquire(1)
        return target.read()
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray): Int {
        maxBytesPerSecond.acquire(b.size)
        return target.read(b)
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray, off: Int, len: Int): Int {
        maxBytesPerSecond.acquire(len)
        return target.read(b, off, len)
    }

    //less important below...
    @Throws(IOException::class)
    override fun skip(n: Long): Long {
        return target.skip(n)
    }

    @Throws(IOException::class)
    override fun available(): Int {
        return target.available()
    }

    @Synchronized
    override fun mark(readlimit: Int) {
        target.mark(readlimit)
    }

    @Synchronized
    @Throws(IOException::class)
    override fun reset() {
        target.reset()
    }

    override fun markSupported(): Boolean {
        return target.markSupported()
    }

    @Throws(IOException::class)
    override fun close() {
        target.close()
    }
}