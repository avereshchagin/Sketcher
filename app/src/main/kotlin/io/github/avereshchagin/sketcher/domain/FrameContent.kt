package io.github.avereshchagin.sketcher.domain

data class FrameContent(
    val operations: List<DrawOperation> = emptyList(),
)
