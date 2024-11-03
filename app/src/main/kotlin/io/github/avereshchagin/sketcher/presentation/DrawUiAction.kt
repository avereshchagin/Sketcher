package io.github.avereshchagin.sketcher.presentation

import androidx.compose.ui.graphics.Color
import io.github.avereshchagin.sketcher.domain.Position

sealed interface DrawUiAction {

    sealed interface Frame : DrawUiAction {

        data object Undo : Frame

        data object Redo : Frame
    }

    sealed interface Tool : DrawUiAction {

        data object Pencil : Tool

        data object Eraser : Tool

        data class Color(val color: androidx.compose.ui.graphics.Color) : Tool
    }

    sealed interface Draw : DrawUiAction {

        data class AddPath(val positions: List<Position>) : Draw
    }
}
