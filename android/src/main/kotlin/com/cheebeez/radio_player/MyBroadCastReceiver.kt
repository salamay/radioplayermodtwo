package com.cheebeez.radio_player

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.security.Provider


class MyBroadCastReceiver(val localBroadcastManager: LocalBroadcastManager?=null,val radioService: RadioPlayerService): BroadcastReceiver() {
    private var localBroadcastM: LocalBroadcastManager?=null

    init {
        localBroadcastM=localBroadcastManager;
    }



    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1!=null) {
            val received = p1.extras?.getString("notification")
            if (received != null) {
                if(received.contains("PAUSE")) {
                    println("PAUSE ACTION")
                    val stateIntent = Intent(RadioPlayerService.ACTION_NOTIFICATION_DATA)
                    stateIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    stateIntent.putExtra(RadioPlayerService.ACTION_NOTIFICATION_EXTRA, "PAUSE")
                    localBroadcastM?.sendBroadcast(stateIntent)
                    radioService.pause()

                }else if(received.contains("NEXT")){
                    println("NEXT ACTION")
                    val stateIntent = Intent(RadioPlayerService.ACTION_NOTIFICATION_DATA)
                    stateIntent.putExtra(RadioPlayerService.ACTION_NOTIFICATION_EXTRA, "NEXT")
                    stateIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    localBroadcastM?.sendBroadcast(stateIntent)

                }else if(received.contains("PREVIOUS")){
                    println("PREVIOUS ACTION")
                    val stateIntent = Intent(RadioPlayerService.ACTION_NOTIFICATION_DATA)
                    stateIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    stateIntent.putExtra(RadioPlayerService.ACTION_NOTIFICATION_EXTRA, "PREVIOUS")
                    localBroadcastM?.sendBroadcast(stateIntent)
                }else if(received.contains("PLAY")){
                    println("PLAY ACTION")
                    val stateIntent = Intent(RadioPlayerService.ACTION_NOTIFICATION_DATA)
                    stateIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    stateIntent.putExtra(RadioPlayerService.ACTION_NOTIFICATION_EXTRA, "PLAY")
                    radioService.play()
                    localBroadcastM?.sendBroadcast(stateIntent)
                }else if(received.contains("STOP")){
                    radioService.stopServ()
                }
            }
        }
    }
}