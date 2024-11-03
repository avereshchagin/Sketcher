package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.ui.theme.Icons

@Composable
fun AnimationTools(
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Undo)
            }
        ) {
            Icon(
                painter = Icons.Undo,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Redo)
            }
        ) {
            Icon(
                painter = Icons.Redo,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = Icons.Bin,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = Icons.Plus,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = Icons.Layers,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = Icons.Pause,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = Icons.Play,
                contentDescription = null
            )
        }
    }
}