package io.github.avereshchagin.sketcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.avereshchagin.sketcher.domain.FramesLogic

object ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            DrawViewModel::class.java -> DrawViewModel(FramesLogic()) as T
            else -> throw IllegalArgumentException("Unknown VM $modelClass")
        }
    }
}
