package com.hourimeche.mvpmatchmovieapp.business.domain.util

class Extensions

fun String.firstCap() = this.replaceFirstChar { it.uppercase() }
fun String?.getYear(): String {
    if (this == null || this.length < 4)
        return ""
    return "(" + this.substring(0, 4) + ")"
}
