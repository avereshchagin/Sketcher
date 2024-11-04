package io.github.avereshchagin.sketcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.avereshchagin.sketcher.domain.FrameContent
import io.github.avereshchagin.sketcher.domain.FramesLogic
import io.github.avereshchagin.sketcher.domain.DrawOperation
import io.github.avereshchagin.sketcher.domain.DrawTool
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrawViewModel(
    private val framesLogic: FramesLogic
) : ViewModel() {

    private val _state = MutableStateFlow(DrawUiState())
    val state: StateFlow<DrawUiState> = _state.asStateFlow()

    private var playJob: Job? = null

    fun onAction(action: DrawUiAction) {
        when (action) {
            is DrawUiAction.Lifecycle -> onLifecycleAction(action)
            is DrawUiAction.Frame -> onFrameAction(action)
            is DrawUiAction.Tool -> onToolAction(action)
            is DrawUiAction.Draw -> onDrawAction(action)
        }
    }

    private fun onLifecycleAction(action: DrawUiAction.Lifecycle) {
        when (action) {
            is DrawUiAction.Lifecycle.Start -> {
                if (_state.value.isPlaying) {
                    playJob?.cancel()
                    playJob = viewModelScope.launch {
                        animate()
                    }
                }
            }
            is DrawUiAction.Lifecycle.Stop -> playJob?.cancel()
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
            is DrawUiAction.Frame.Add -> {
                framesLogic.updateCurrentFrame(FrameContent(_state.value.operations))
                val uuid = framesLogic.addFrame()
                _state.update { state ->
                    state.copy(
                        currentFrameUuid = uuid,
                        operations = ArrayList(),
                        prevFrameOperations = state.operations,
                        lastOperation = 0,
                    )
                }
            }
            is DrawUiAction.Frame.Copy -> {
                framesLogic.updateCurrentFrame(FrameContent(_state.value.operations))
                val uuid = framesLogic.duplicate()
                _state.update { state ->
                    state.copy(
                        currentFrameUuid = uuid,
                        operations = state.operations,
                        prevFrameOperations = state.operations,
                        lastOperation = state.operations.size,
                    )
                }
            }
            is DrawUiAction.Frame.DeleteAll -> {
                framesLogic.clear()
                val uuid = framesLogic.getCurrentVisibleFrames().currentFrameUuid
                _state.update { state ->
                    state.copy(
                        currentFrameUuid = uuid,
                        operations = ArrayList(),
                        prevFrameOperations = emptyList(),
                        lastOperation = 0,
                    )
                }
            }
            is DrawUiAction.Frame.Delete -> {
                framesLogic.deleteCurrentFrame()
                val visibleFrames = framesLogic.getCurrentVisibleFrames()
                _state.update { state ->
                    val uuid = visibleFrames.currentFrameUuid
                    val operations = visibleFrames.currentFrame.operations.toMutableList()
                    state.copy(
                        currentFrameUuid = uuid,
                        operations = operations,
                        prevFrameOperations = visibleFrames.previousFrame?.operations?.toMutableList() ?: emptyList(),
                        lastOperation = operations.size,
                    )
                }
            }
            is DrawUiAction.Frame.TogglePlayPause -> {
                if (_state.value.isPlaying || playJob?.isActive == true) {
                    viewModelScope.launch {
                        playJob?.cancelAndJoin()
                        framesLogic.resetToLast()
                        val visibleFrames = framesLogic.getCurrentVisibleFrames()
                        val uuid = visibleFrames.currentFrameUuid
                        val operations = visibleFrames.currentFrame.operations.toMutableList()
                        _state.update { state ->
                            state.copy(
                                isPlaying = false,
                                currentFrameUuid = uuid,
                                operations = operations,
                                prevFrameOperations = visibleFrames.previousFrame?.operations?.toMutableList() ?: emptyList(),
                                lastOperation = operations.size,
                            )
                        }
                    }
                } else {
                    playJob?.cancel()
                    playJob = viewModelScope.launch {
                        framesLogic.updateCurrentFrame(FrameContent(_state.value.operations))
                        animate()
                    }
                }
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

    private suspend fun animate() {
        framesLogic.resetToBeginning()
        while (true) {
            val visibleFrames = framesLogic.getCurrentVisibleFrames()
            val uuid = visibleFrames.currentFrameUuid
            val operations = visibleFrames.currentFrame.operations.toMutableList()
            _state.update { state ->
                state.copy(
                    isPlaying = true,
                    currentFrameUuid = uuid,
                    operations = operations,
                    prevFrameOperations = emptyList(),
                    lastOperation = operations.size,
                )
            }

            delay(1000)
            framesLogic.nextFrame(isLoop = true)
        }
    }
}
