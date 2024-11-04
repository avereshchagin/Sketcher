package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.ui.theme.Icons

@Composable
fun AnimationTools(
    isPlaying: Boolean,
    canUndo: Boolean,
    canRedo: Boolean,
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Undo)
            },
            enabled = !isPlaying && canUndo,
        ) {
            Icon(
                painter = Icons.Undo,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Redo)
            },
            enabled = !isPlaying && canRedo,
        ) {
            Icon(
                painter = Icons.Redo,
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.DeleteAll)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Cross,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Delete)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Bin,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Add)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Plus,
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.Copy)
            },
            enabled = !isPlaying,
        ) {
            Icon(
                painter = Icons.Copy,
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        IconButton(
            onClick = {
                onAction(DrawUiAction.Frame.TogglePlayPause)
            }
        ) {
            Icon(
                painter = if (isPlaying) Icons.Pause else Icons.Play,
                contentDescription = null
            )
        }
    }
}