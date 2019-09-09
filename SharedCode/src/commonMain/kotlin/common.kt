package com.ableandroid.kmpdemo

expect fun platformName(): String
expect fun timestamp(): Int
expect fun logIt(message: String, level: String = "WARN", tag: String = "notag")

val labelName = "Kotlin/Everywhere PHX"

fun createApplicationScreenMessage() : String {
    return "$labelName LOVES: ${platformName()}"
}

fun createTimestampScreenMessage(): String {
    return "Timestamp is: ${timestamp()}"
}

fun pubLog(message: String, level: String = "WARN", tag: String = "notag") {
    logIt(message, level, tag)
}
