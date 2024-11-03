package io.github.avereshchagin.sketcher.presentation

import androidx.lifecycle.ViewModel
import io.github.avereshchagin.sketcher.domain.DrawOperation
import io.github.avereshchagin.sketcher.domain.DrawTool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DrawViewModel : ViewModel() {

    private val _state = MutableStateFlow(DrawUiState())
    val state: StateFlow<DrawUiState> = _state.asStateFlow()

    fun onAction(action: DrawUiAction) {
        when (action) {
            is DrawUiAction.Frame -> onFrameAction(action)
            is DrawUiAction.Tool -> onToolAction(action)
            is DrawUiAction.Draw -> onDrawAction(action)
        }
    }

    private fun onFrameAction(action: DrawUiAction.Frame) {
        when (action) {
            is DrawUiAction.Frame.Undo -> _state.update { state ->
                state.copy(
                    lastOperation = (state.lastOperation - 1).coerceAtLeast(0)
                )
            }
            is DrawUiAction.Frame.Redo -> _state.update { state ->
                state.copy(
                    lastOperation = (state.lastOperation + 1).coerceAtMost(state.operations.size)
                )
            }
        }
    }

    private fun onToolAction(action: DrawUiAction.Tool) {
        when (action) {
            is DrawUiAction.Tool.Pencil -> _state.update { state ->
                state.copy(currentTool = DrawTool.PENCIL)
            }
            is DrawUiAction.Tool.Eraser -> _state.update { state ->
                state.copy(currentTool = DrawTool.ERASER)
            }
            is DrawUiAction.Tool.Color -> _state.update { state ->
                state.copy(
                    currentColor = action.color
                )
            }
        }
    }

    private fun onDrawAction(action: DrawUiAction.Draw) {
        when (action) {
            is DrawUiAction.Draw.AddPath -> {
                _state.update { state ->
                    state.copy(
                        operations = state.operations.take(state.lastOperation).toMutableList().apply {
                            add(DrawOperation(
                                positions = action.positions,
                                tool = state.currentTool,
                                color = state.currentColor,
                            ))
                        },
                        lastOperation = state.lastOperation + 1
                    )
                }
            }
        }
    }
}
