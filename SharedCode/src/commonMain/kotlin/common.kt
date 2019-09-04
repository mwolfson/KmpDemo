package com.ableandroid.kmpdemo

expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin/Everywhere PHX LOVES: ${platformName()}"
}