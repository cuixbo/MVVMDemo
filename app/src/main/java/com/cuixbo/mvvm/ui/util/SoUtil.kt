package com.cuixbo.mvvm.ui.util

class SoUtil {

    companion object {
        init {
            val soPath = "/data/data/com.cuixbo.mvvm/cache/libtwo-lib.so"
            System.load(soPath)
        }

        @JvmStatic
        external fun stringFromJNI(): String?

        @JvmStatic
        external fun stringFromJNI2(code: String?): String?
    }


}