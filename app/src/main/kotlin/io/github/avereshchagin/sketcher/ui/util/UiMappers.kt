package io.github.avereshchagin.sketcher.ui.util

import androidx.compose.ui.geometry.Offset
import io.github.avereshchagin.sketcher.domain.Position

fun Offset.toPosition() = Position(x, y)

