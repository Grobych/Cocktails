package com.globa.cocktails.domain.editlog

import com.globa.cocktails.datalayer.database.editlog.EditRecipeLogDBModel

data class EditRecipeLog(
    val name: String,
    val dateTime: Long
)

fun EditRecipeLogDBModel.toDomainModel() = EditRecipeLog(
    name = name,
    dateTime = dateTime
)

fun EditRecipeLog.toDBModel() = EditRecipeLogDBModel(
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
