package io.github.avereshchagin.sketcher.data

import java.util.UUID

class FrameNode(
    var content: FrameContent,
    var prev: FrameNode? = null,
    var next: FrameNode? = null,
) {

    val uuid = UUID.randomUUID().toString()
}
