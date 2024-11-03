package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.ui.theme.ModalBackground
import io.github.avereshchagin.sketcher.ui.theme.ModalBorder
import io.github.avereshchagin.sketcher.ui.util.ImmutableList

val defaultPalette = listOf(
    Color(0xFFFFFECC),
    Color(0xFFFF95D5),
    Color(0xFFFFD1A9),
    Color(0xFFEDCAFF),
    Color(0xFFCCF3FF),

    Color(0xFFF3ED00),
    Color(0xFFF8D3E3),
    Color(0xFFFA9A46),
    Color(0xFFB18CFE),
    Color(0xFF94E4FD),

    Color(0xFFA8DB10),
    Color(0xFFFB66A4),
    Color(0xFFFC7600),
    Color(0xFF9747FF),
    Color(0xFF00C9FB),

    Color(0xFF75BB41),
    Color(0xFFDC0057),
    Color(0xFFED746C),
    Color(0xFF4D21B2),
    Color(0xFF73A8FC),

    Color(0xFF4E7A25),
    Color(0xFF9D234C),
    Color(0xFFFF3D00),
    Color(0xFF641580),
    Color(0xFF1976D2),
)

@Composable
fun FullPalette(
    colors: ImmutableList<Color>,
    selectedColor: Color,
    onSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .background(
                color = ModalBackground,
                shape = RoundedCornerShape(4.dp),
            )
            .border(
                width = 1.dp,
                color = ModalBorder,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        repeat((colors.items.size - 1) / 5 + 1) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                repeat(5) { i ->
                    val color = colors.items.getOrNull(row * 5 + i)
                    if (color != null) {
                        ColorItem(
                            modifier = Modifier
                                .width(28.dp)
                                .height(28.dp)
                                .clickable {
                                    onSelected(color)
                                },
                            color = color,
                            isSelected = selectedColor == color,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PalettePreview() {
    FullPalette(
        colors = ImmutableList(defaultPalette),
        selectedColor = defaultPalette.first(),
        onSelected = {},
    )
}
