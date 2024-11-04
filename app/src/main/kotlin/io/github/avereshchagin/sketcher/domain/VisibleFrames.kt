package io.github.avereshchagin.sketcher.domain

class VisibleFrames(
    val currentFrameUuid: String,
    val currentFrame: FrameContent,
    val previousFrame: FrameContent?,
)