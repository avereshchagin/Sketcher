package io.github.avereshchagin.sketcher.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import io.github.avereshchagin.sketcher.R
import io.github.avereshchagin.sketcher.domain.DrawTool
import io.github.avereshchagin.sketcher.domain.Position
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.ui.util.AndroidOperation

@Composable
fun DrawableCanvas(
    isPlaying: Boolean,
    paths: SnapshotStateList<AndroidOperation>,
    prevFramePaths: SnapshotStateList<AndroidOperation>,
    currentTool: DrawTool,
    currentColor: Color,
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentPath = remember {
        AndroidPath()
    }

    var drawNonce by remember {
        mutableIntStateOf(0)
    }

    val backgroundBrush = ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.canvas_bg)))

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clipToBounds()
            .pointerInput(isPlaying) {
                if (!isPlaying) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> {
                                    val position = event.changes.first().position
                                    currentPath.moveTo(position.x, position.y)
                                    drawNonce++
                                }

                                PointerEventType.Move -> {
                                    event.changes.fastForEach {
                                        currentPath.lineTo(it.position.x, it.position.y)
                                    }
                                    drawNonce++
                                }

                                PointerEventType.Release -> {
                                    val positions = ArrayList<Position>()
                                    for (segment in currentPath) {
                                        when (segment.type) {
                                            PathSegment.Type.Move -> positions.add(
                                                Position(
                                                    segment.points[0],
                                                    segment.points[1]
                                                )
                                            )

                                            PathSegment.Type.Line -> positions.add(
                                                Position(
                                                    segment.points[2],
                                                    segment.points[3]
                                                )
                                            )

                                            else -> {}
                                        }
                                    }
                                    onAction(DrawUiAction.Draw.AddPath(positions))
                                    currentPath.reset()
                                }
                            }
                        }
                    }
                }
            }
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
    ) {
        drawRect(brush = backgroundBrush)

        if (!isPlaying) {
            drawWithLayer {
                prevFramePaths.fastForEach { path ->
                    drawPath(path.path, path.tool, path.color, alpha = 0.5f)
                }
            }
        }

        drawWithLayer {
            paths.fastForEach { path ->
                drawPath(path.path, path.tool, path.color)
            }

            if (!isPlaying) {
                drawNonce.let {
                    drawPath(currentPath, currentTool, currentColor)
                }
            }
        }
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

private fun DrawScope.drawPath(path: AndroidPath, tool: DrawTool, color: Color, alpha: Float = 1f) {
    when (tool) {
        DrawTool.PENCIL -> drawPath(
            path = path,
            color = color,
            style = Stroke(width = 10f),
            blendMode = BlendMode.SrcOver,
            alpha = alpha,
        )

        DrawTool.ERASER -> drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = 30f),
            blendMode = BlendMode.Clear
        )
    }
}
