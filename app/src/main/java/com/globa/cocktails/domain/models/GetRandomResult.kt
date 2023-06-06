package com.globa.cocktails.domain.models

sealed class GetRandomResult() {
    data class Success(val id: String): GetRandomResult()
    data class Error(val message: String): GetRandomResult()
}