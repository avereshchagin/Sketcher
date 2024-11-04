package io.github.avereshchagin.sketcher.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SketcherPalette(
    val selectedColor: Color = Color.Unspecified,
    val normalColor: Color = Color.Unspecified,
    val disabledColor: Color = Color.Unspecified,
)

val LocalSketcherPalette = staticCompositionLocalOf { SketcherPalette() }

@Composable
fun SketcherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    val rippleIndication = ripple(bounded = false)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {

        CompositionLocalProvider(
            LocalIndication provides rippleIndication,
            content = content,
        )
    }
}