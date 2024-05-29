package com.example.claculater.ui.main.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.example.claculater.R

class AppLockService:Service() {
    val SEDRVICE_ID = 1
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "my_channel_1"
            val channel = NotificationChannel(channelID, "default", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val notification = Notification.Builder(this, channelID).apply {
                setContentTitle("Notification Title")
                setContentText("This is Content")
                setSmallIcon(R.drawable.ic_launcher_background)
            }.build()
            startForeground(SEDRVICE_ID, notification)
        }
        showMessage()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showMessage() {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(applicationContext, " Hi this is Service ", Toast.LENGTH_SHORT).show()
        },5000)
    }
}