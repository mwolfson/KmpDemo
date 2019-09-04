package com.ableandroid.kmpdemo

import android.os.Build

actual fun platformName(): String {

    return "Android Version: ${Build.VERSION.SDK_INT}"
}