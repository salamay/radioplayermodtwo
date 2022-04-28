package com.cheebeez.radio_player

import android.support.v4.media.session.MediaSessionCompat

class MyCallBack: MediaSessionCompat.Callback() {
    override fun onPlay() {
        super.onPlay()
        println("CALLBACK PLAY")
    }

    override fun onPause() {
        super.onPause()
        println("CALLBACK PAUSE")

    }

    override fun onSkipToNext() {
        super.onSkipToNext()
        println("CALLBACK SKIP")

    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
        println("CALLBACK PREVIOUS")

    }
}