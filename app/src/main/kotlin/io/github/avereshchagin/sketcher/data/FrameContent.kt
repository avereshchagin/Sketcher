package io.github.avereshchagin.sketcher.data

import io.github.avereshchagin.sketcher.domain.DrawOperation

data class FrameContent(
    val operations: List<DrawOperation> = emptyList(),
)
