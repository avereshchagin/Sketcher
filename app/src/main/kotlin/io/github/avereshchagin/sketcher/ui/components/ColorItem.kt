package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.ui.theme.Green
import io.github.avereshchagin.sketcher.ui.util.ImmutableList

@Composable
fun ColorItem(
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier,
    ) {
        drawCircle(color)

        if (isSelected) {
            drawCircle(
                color = Green,
                style = Stroke(width = 1.5.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
private fun ColorItemPreview() {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ColorItem(
            modifier = Modifier.size(28.dp),
            isSelected = false,
            color = Color.Blue,
        )

        ColorItem(
            modifier = Modifier.size(28.dp),
            isSelected = true,
            color = Color.Blue,
        )
    }
}
