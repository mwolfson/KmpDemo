package com.ableandroid.kmpdemo

import platform.UIKit.UIDevice

actual fun platformName(): String {
    return "iOS:" + UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}