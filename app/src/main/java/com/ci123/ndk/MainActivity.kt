package com.ci123.ndk

import android.annotation.SuppressLint

class MainActivity {
    @SuppressLint("SdCardPath")
    companion object {
        init {
            val soPath = "/data/data/com.cuixbo.mvvm/cache/libtwo-lib2.so"
            System.load(soPath)
            // java.lang.UnsatisfiedLinkError:
            // dlopen failed: library "/data/data/com.cuixbo.mvvm/cache/libtwo-lib2.so" not found
        }

        @JvmStatic
        external fun stringFromJNI(): String?

        @JvmStatic
        external fun stringFromJNI2(code: String?): String?
    }

}