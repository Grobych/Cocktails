package com.globa.cocktails.utils

fun List<String>.contains(s: String, ignoreCase: Boolean = false): Boolean {
    return any { it.equals(s, ignoreCase) }
}