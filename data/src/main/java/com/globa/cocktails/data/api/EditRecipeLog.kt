package com.globa.cocktails.data.api

data class EditRecipeLog(
    val name: String,
    val dateTime: Long
)

fun com.globa.cocktails.data.internal.database.editlog.EditRecipeLogDBModel.toDomainModel() = EditRecipeLog(
    name = name,
    dateTime = dateTime
)

fun EditRecipeLog.toDBModel() =
    com.globa.cocktails.data.internal.database.editlog.EditRecipeLogDBModel(
        name = name,
        dateTime = dateTime
    )

fun List<EditRecipeLog>.contains(name: String): Boolean {
    var res = false
    this.forEach {
        if (it.name == name) res = true
    }
    return res
}
