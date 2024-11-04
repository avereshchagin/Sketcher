package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.ui.util.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSelector(
    selectedColor: Color,
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val scope = rememberCoroutineScope()
    val state = rememberTooltipState(isPersistent = true)
    var isFull by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        if (state.isVisible) {
            Popup(
                popupPositionProvider = rememberPopupPositionProvider(),
                onDismissRequest = {
                    if (isFull) {
                        isFull = false
                    } else if (state.isVisible) {
                        scope.launch { state.dismiss() }
                    }
                },
                properties = PopupProperties()
            ) {

                Column {
                    if (isFull) {
                        FullPalette(
                            colors = ImmutableList(defaultPalette),
                            selectedColor = selectedColor,
                            onSelected = {
                                scope.launch { state.dismiss() }
                                onAction(DrawUiAction.Tool.Color(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    ShortPalette(
                        colors = ImmutableList(shortPalette),
                        selectedColor = selectedColor,
                        isFullOpen = isFull,
                        onSelected = {
                            scope.launch { state.dismiss() }
                            onAction(DrawUiAction.Tool.Color(it))
                        },
                        onOpenFull = {
                            isFull = true
                        }
                    )
                }
            }
        }

        ColorItem(
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .then(
                    if (enabled) {
                        Modifier.clickable {
                            isFull = false
                            scope.launch { state.show() }
                        }
                    } else Modifier
                ),
            color = selectedColor,
            isSelected = state.isVisible
        )
    }
}

@Composable
fun rememberPopupPositionProvider(
    spacingBetweenTooltipAndAnchor: Dp = 16.dp
): PopupPositionProvider {
    val tooltipAnchorSpacing =
        with(LocalDensity.current) { spacingBetweenTooltipAndAnchor.roundToPx() }
    return remember {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                val x = (windowSize.width - popupContentSize.width) / 2
                var y = anchorBounds.top - popupContentSize.height - tooltipAnchorSpacing
                if (y < 0) y = anchorBounds.bottom + tooltipAnchorSpacing
                return IntOffset(x, y)
            }
        }
    }
}
