package io.github.avereshchagin.sketcher.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import io.github.avereshchagin.sketcher.R

object Icons {

    val Undo: Painter
        @Composable
        get() = painterResource(R.drawable.ic_undo)

    val Redo: Painter
        @Composable
        get() = painterResource(R.drawable.ic_redo)

    val Bin: Painter
        @Composable
        get() = painterResource(R.drawable.ic_bin)

    val Plus: Painter
        @Composable
        get() = painterResource(R.drawable.ic_plus)

    val Copy: Painter
        @Composable
        get() = painterResource(R.drawable.ic_copy)

    val Cross: Painter
        @Composable
        get() = painterResource(R.drawable.ic_cross)

    val Layers: Painter
        @Composable
        get() = painterResource(R.drawable.ic_layers)

    val Pause: Painter
        @Composable
        get() = painterResource(R.drawable.ic_pause)

    val Play: Painter
        @Composable
        get() = painterResource(R.drawable.ic_play)

    val Pencil: Painter
        @Composable
        get() = painterResource(R.drawable.ic_pencil)

    val Brush: Painter
        @Composable
        get() = painterResource(R.drawable.ic_brush)

    val Eraser: Painter
        @Composable
        get() = painterResource(R.drawable.ic_eraser)

    val Instruments: Painter
        @Composable
        get() = painterResource(R.drawable.ic_instruments)

    val Palette: Painter
        @Composable
        get() = painterResource(R.drawable.ic_palette)
}