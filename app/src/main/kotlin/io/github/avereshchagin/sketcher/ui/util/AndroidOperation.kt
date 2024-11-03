package io.github.avereshchagin.sketcher.ui.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Color
import io.github.avereshchagin.sketcher.domain.DrawOperation
import io.github.avereshchagin.sketcher.domain.DrawTool

data class AndroidOperation(
    val color: Color,
    val tool: DrawTool,
    val path: AndroidPath,
)

fun List<DrawOperation>.toAndroidOperations(takeCount: Int): SnapshotStateList<AndroidOperation> {
    val paths = SnapshotStateList<AndroidOperation>()
    for (j in 0 ..< size.coerceAtMost(takeCount)) {
        val action = get(j)
        val path = AndroidPath()
        if (action.positions.size > 2) {
            path.moveTo(action.positions[0].x, action.positions[0].y)
            for (i in 1..< action.positions.size) {
                path.lineTo(action.positions[i].x, action.positions[i].y)
            }
        }
        paths.add(AndroidOperation(action.color, action.tool, path))
    }
    return paths
}
