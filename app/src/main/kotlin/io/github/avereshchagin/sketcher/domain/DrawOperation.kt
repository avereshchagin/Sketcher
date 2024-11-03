package io.github.avereshchagin.sketcher.domain

import androidx.compose.ui.graphics.Color

data class DrawOperation(
    val positions: List<Position>,
    val tool: DrawTool,
    val color: Color,
)
