package com.ableandroid.kmpdemo

import platform.Foundation.NSLog
import platform.QuartzCore.CACurrentMediaTime
import platform.UIKit.UIDevice

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            ": " + UIDevice.currentDevice.systemVersion
}

actual fun timestamp(): Int {
    return CACurrentMediaTime().toInt()
}

actual fun logIt(message: String, level: String, tag: String) {
    NSLog("$level::$tag $message")
}