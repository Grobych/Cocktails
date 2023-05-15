package com.globa.cocktails.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.globa.cocktails.R

val roboto = FontFamily(
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto, weight = FontWeight.Normal),
)

val typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    )
)

@Composable
@Preview
fun TypoPreview(){
    AppTheme {
        Surface {
            Column {
                Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
                Text(text = "Headline Medium", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Headline Small", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Title Large", style = MaterialTheme.typography.titleLarge)
                Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
                Text(text = "Title Small", style = MaterialTheme.typography.titleSmall)
                Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Body Small", style = MaterialTheme.typography.bodySmall)
                Text(text = "Label Large", style = MaterialTheme.typography.labelLarge)
                Text(text = "Label Medium", style = MaterialTheme.typography.labelMedium)
                Text(text = "Label Small", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}