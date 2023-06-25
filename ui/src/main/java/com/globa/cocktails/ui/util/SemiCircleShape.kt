package com.globa.cocktails.ui.util

import android.graphics.Matrix
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

val SemiCircleShape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 217
        val baseHeight = 335

        val pathData = "M166.738 0.0891113H217V334.089H166.738C74.6513 334.089 0 259.321 0 167.089C0 74.8575 74.6513 0.0891113 166.738 0.0891113Z"
        val path = PathParser.createPathFromPathData(pathData)
        return Outline.Generic(
            path
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}