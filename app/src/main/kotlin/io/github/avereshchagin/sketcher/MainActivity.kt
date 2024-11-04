package io.github.avereshchagin.sketcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.avereshchagin.sketcher.presentation.DrawUiAction
import io.github.avereshchagin.sketcher.presentation.DrawUiState
import io.github.avereshchagin.sketcher.presentation.DrawViewModel
import io.github.avereshchagin.sketcher.presentation.ViewModelFactory
import io.github.avereshchagin.sketcher.ui.components.AnimationTools
import io.github.avereshchagin.sketcher.ui.components.DrawableCanvas
import io.github.avereshchagin.sketcher.ui.components.DrawingTools
import io.github.avereshchagin.sketcher.ui.theme.SketcherTheme
import io.github.avereshchagin.sketcher.ui.util.toAndroidOperations

class MainActivity : ComponentActivity() {

    private val viewModel: DrawViewModel by viewModels { ViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SketcherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        uiState = viewModel.state.collectAsState(),
                        onAction = viewModel::onAction,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    uiState: State<DrawUiState>,
    onAction: (DrawUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by uiState

    Column(modifier = modifier) {
        AnimationTools(
            modifier = Modifier.fillMaxWidth(),
            isPlaying = state.isPlaying,
            canUndo = state.lastOperation > 0,
            canRedo = state.lastOperation < state.operations.size,
            onAction = onAction
        )

        val paths = remember(state.currentFrameUuid, state.lastOperation) {
            state.operations.toAndroidOperations(state.lastOperation)
        }
        val prevFramePaths = remember(state.currentFrameUuid, state.prevFrameOperations) {
            state.prevFrameOperations.toAndroidOperations(state.prevFrameOperations.size)
        }

        DrawableCanvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            isPlaying = state.isPlaying,
            currentTool = state.currentTool,
            currentColor = state.currentColor,
            paths = paths,
            prevFramePaths = prevFramePaths,
            onAction = onAction,
        )

        DrawingTools(
            modifier = Modifier.fillMaxWidth(),
            isPlaying = state.isPlaying,
            selectedTool = state.currentTool,
            selectedColor = state.currentColor,
            onAction = onAction,
        )
    }
}
