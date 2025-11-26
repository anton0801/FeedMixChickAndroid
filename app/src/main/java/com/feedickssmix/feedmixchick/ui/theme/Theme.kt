package com.feedickssmix.feedmixchick.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val WheatStart = Color(0xFFFBE8A6)
val WheatEnd = Color(0xFFFFD93D)
val GreenHealth = Color(0xFF3DD598)
val RedWarning = Color(0xFFFF6B6B)
val GoldBrown = Color(0xFFCBA35C)
val Cream = Color(0xFFFFFDF6)

val WheatGradient = Brush.verticalGradient(listOf(WheatStart, WheatEnd))

val Green      = Color(0xFF3DD598)
val Red        = Color(0xFFFF6B6B)
val Gold       = Color(0xFFCBA35C)

private val LightColors = lightColorScheme(
    primary = GreenHealth,
    secondary = GoldBrown,
    background = WheatStart,
    surface = Cream,
    error = RedWarning,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun FeedMixChickTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}