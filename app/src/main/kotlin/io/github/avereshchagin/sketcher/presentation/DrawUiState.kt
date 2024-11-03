package io.github.avereshchagin.sketcher.presentation

import androidx.compose.ui.graphics.Color
import io.github.avereshchagin.sketcher.domain.DrawOperation
import io.github.avereshchagin.sketcher.domain.DrawTool

data class DrawUiState(
    val operations: MutableList<DrawOperation> = ArrayList(),
    val lastOperation: Int = 0,
    val currentTool: DrawTool = DrawTool.PENCIL,
    val currentColor: Color = Color.Black,
)
