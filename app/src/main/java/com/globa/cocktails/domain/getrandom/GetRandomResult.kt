package com.globa.cocktails.domain.getrandom

sealed class GetRandomResult() {
    data class Success(val id: Int): GetRandomResult()
    data class Error(val message: String): GetRandomResult()
}