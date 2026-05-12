package com.arabicpdftoword.app.core.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var soundPool: SoundPool? = null
    private var clickSoundId: Int = 0
    private var successSoundId: Int = 0
    private var errorSoundId: Int = 0
    private var convertCompleteSoundId: Int = 0
    private var isInitialized = false
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            initialize()
        }
    }

    private fun initialize() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        loadSounds()
        isInitialized = true
    }

    private fun loadSounds() {
        try {
            val res = context.resources
            clickSoundId = 1
            successSoundId = 2
            errorSoundId = 3
            convertCompleteSoundId = 4
        } catch (_: Exception) {
        }
    }

    fun playClick() {
        playSound(clickSoundId)
    }

    fun playSuccess() {
        playSound(successSoundId)
    }

    fun playError() {
        playSound(errorSoundId)
    }

    fun playConvertComplete() {
        playSound(convertCompleteSoundId)
    }

    private fun playSound(soundId: Int) {
        if (!isInitialized || soundPool == null) return
        try {
            soundPool?.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
        } catch (_: Exception) {
        }
    }

    fun release() {
        try {
            soundPool?.release()
            soundPool = null
            isInitialized = false
        } catch (_: Exception) {
        }
    }
}
