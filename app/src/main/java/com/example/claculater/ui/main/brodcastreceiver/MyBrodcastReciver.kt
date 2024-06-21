package com.example.claculater.ui.main.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.startForegroundService
import com.example.claculater.ui.main.service.AppLockService

class MyBrodcastReciver:BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action==Intent.ACTION_BOOT_COMPLETED) {
            val intent2 = Intent(p0, AppLockService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                p0?.startForegroundService(intent2)
            else
                p0?.startService(intent2)
        }
        if (p1?.action == Intent.ACTION_SCREEN_ON){
            Log.i("fff", " this is package name ${p1.`package`.toString()}")
            if (p1.`package`=="org.telegram.messenger"){
                val intent2 = Intent(p0, AppLockService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    p0?.startForegroundService(intent2)
                else
                    p0?.startService(intent2)
            }

        }

    }

}