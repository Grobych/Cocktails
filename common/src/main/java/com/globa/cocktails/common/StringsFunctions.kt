package com.globa.cocktails.common

fun List<String>.contains(s: String, ignoreCase: Boolean = false): Boolean {
    return any { it.equals(s, ignoreCase) }
}