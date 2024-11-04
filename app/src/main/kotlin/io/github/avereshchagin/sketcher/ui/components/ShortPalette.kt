package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.ui.theme.Colors
import io.github.avereshchagin.sketcher.ui.theme.Icons
import io.github.avereshchagin.sketcher.ui.util.ImmutableList

val shortPalette = listOf(
    Color(0xFFFFFFFF),
    Color(0xFFFF3D00),
    Color(0xFF000000),
    Color(0xFF1976D2),
)

@Composable
fun ShortPalette(
    colors: ImmutableList<Color>,
    selectedColor: Color,
    isFullOpen: Boolean,
    onSelected: (Color) -> Unit,
    onOpenFull: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Colors.ModalBackground,
                shape = RoundedCornerShape(4.dp),
            )
            .border(
                width = 1.dp,
                color = Colors.ModalBorder,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = onOpenFull,
        ) {
            Icon(
                tint = if (isFullOpen) Colors.Selected else Color.Black,
                painter = Icons.Palette,
                contentDescription = null,
            )
        }

        repeat(4) { i ->
            val color = colors.items.getOrNull(i)
            if (color != null) {
                ColorItem(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clickable {
                            onSelected(color)
                        },
                    color = color,
                    isSelected = selectedColor == color
                )
            }
        }
    }
}

@Preview
@Composable
private fun PalettePreview() {
    FullPalette(
        colors = ImmutableList(shortPalette),
        selectedColor = shortPalette.first(),
        onSelected = {}
    )
}
