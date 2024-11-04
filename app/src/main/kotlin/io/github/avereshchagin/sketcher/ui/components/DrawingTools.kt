package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.domain.DrawTool
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.ui.theme.Green
import io.github.avereshchagin.sketcher.ui.theme.Icons

@Composable
fun DrawingTools(
    isPlaying: Boolean,
    selectedTool: DrawTool,
    selectedColor: Color,
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                onAction(DrawUiAction.Tool.Pencil)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Pencil,
                tint = if (selectedTool == DrawTool.PENCIL) Green else Color.Black,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Tool.Eraser)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Eraser,
                tint = if (selectedTool == DrawTool.ERASER) Green else Color.Black,
                contentDescription = null
            )
        }

        ColorSelector(
            modifier = Modifier.padding(8.dp),
            enabled = !isPlaying,
            selectedColor = selectedColor,
            onAction = onAction
        )
    }
}