package io.github.avereshchagin.sketcher.ui.util

import androidx.compose.runtime.Immutable

@Immutable
@JvmInline
value class ImmutableList<T>(val items: List<T>)