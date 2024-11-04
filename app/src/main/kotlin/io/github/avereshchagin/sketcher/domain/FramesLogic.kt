package io.github.avereshchagin.sketcher.domain

import android.util.Log

class FramesLogic {

    private var firstFrame: FrameNode? = null
    private var lastFrame: FrameNode? = null
    private var currentFrame: FrameNode = FrameNode(FrameContent())

    init {
        firstFrame = currentFrame
        lastFrame = currentFrame
    }

    fun updateCurrentFrame(content: FrameContent) {
        currentFrame.content = content
    }

    fun addFrame(): String {
        val frame = FrameNode(FrameContent())
        frame.prev = currentFrame
        frame.next = currentFrame.next
        if (lastFrame == currentFrame) {
            lastFrame = frame
        }
        currentFrame.next?.prev = frame
        currentFrame.next = frame
        currentFrame = frame

        return currentFrame.uuid
    }

    fun duplicate(): String {
        val frame = FrameNode(currentFrame.content)
        frame.prev = currentFrame
        frame.next = currentFrame.next
        if (lastFrame == currentFrame) {
            lastFrame = frame
        }
        currentFrame.next?.prev = frame
        currentFrame.next = frame
        currentFrame = frame

        return currentFrame.uuid
    }

    fun clear() {
        currentFrame = FrameNode(FrameContent())
        firstFrame = currentFrame
        lastFrame = currentFrame
    }

    fun deleteCurrentFrame() {
        if (lastFrame == currentFrame) {
            lastFrame = currentFrame.prev
        }
        if (firstFrame == currentFrame) {
            firstFrame = currentFrame.next
        }

        currentFrame.prev?.next = currentFrame.next
        currentFrame.next?.prev = currentFrame.prev
        currentFrame = currentFrame.prev ?: currentFrame.next ?: FrameNode(FrameContent())

        if (lastFrame == null) {
            lastFrame = currentFrame
        }
        if (firstFrame == null) {
            firstFrame = currentFrame
        }
    }

    fun getCurrentVisibleFrames(): VisibleFrames {
        return VisibleFrames(
            currentFrameUuid = currentFrame.uuid,
            currentFrame = currentFrame.content,
            previousFrame = currentFrame.prev?.content
        )
    }

    fun resetToBeginning() {
        currentFrame = firstFrame ?: FrameNode(FrameContent())
    }

    fun resetToLast() {
        currentFrame = lastFrame ?: FrameNode(FrameContent())
    }

    fun nextFrame(isLoop: Boolean) {
        val next = currentFrame.next
        if (next != null) {
            currentFrame = next
        } else if (isLoop) {
            resetToBeginning()
        }
    }

    fun log() {
        var frame = firstFrame
        while (frame != null) {
            Log.i("DRAW", "frame: ${frame.uuid}")
            frame = frame.next
        }
        Log.i("DRAW", "")
    }
}
