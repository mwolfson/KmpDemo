package com.ableandroid.kmpdemo

import android.content.Context
import android.os.Build
import android.util.Log
import java.util.*


actual fun platformName(): String {

    return "Android Version: ${Build.VERSION.SDK_INT}"
}

actual fun timestamp(): Int {
    return System.currentTimeMillis().toInt()
}

actual fun logIt(message: String, level: String, tag: String) {
    when (level) {
        "DEBUG" -> Log.d(tag, message)
        "WARN" -> Log.w(tag, message)
        "ERROR" -> Log.e(tag, message)
        else -> Log.d(tag, message)
    }
}
