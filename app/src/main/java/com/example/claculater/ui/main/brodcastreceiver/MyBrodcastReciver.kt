package com.example.claculater.ui.main.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService
import com.example.claculater.ui.main.service.AppLockService

class MyBrodcastReciver:BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent2 = Intent(p0, AppLockService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            p0?.startForegroundService(intent2)
        else
            p0?.startService(intent2)
    }

}