package com.globa.cocktails.domain.models

data class ReceipePreview(
    val id: String,
    val name: String,
    val imageURL: String,
    val isFavorite: Boolean,
    val tags: List<String>
)
