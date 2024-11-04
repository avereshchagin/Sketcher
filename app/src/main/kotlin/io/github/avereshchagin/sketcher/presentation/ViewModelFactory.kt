package io.github.avereshchagin.sketcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.avereshchagin.sketcher.data.FramesRepository

object ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            DrawViewModel::class.java -> DrawViewModel(FramesRepository()) as T
            else -> throw IllegalArgumentException("Unknown VM $modelClass")
        }
    }
}
