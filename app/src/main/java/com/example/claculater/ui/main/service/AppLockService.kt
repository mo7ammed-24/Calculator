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
import androidx.annotation.RequiresApi
import com.example.claculater.R
import com.example.claculater.ui.main.activities.LockActivity

class AppLockService:Service() {
    val SEDRVICE_ID = 1
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showMessage()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showMessage() {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(applicationContext, " Hi this is Service ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LockActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        },5000)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(){
        val channelID = "my_channel_1"
        val channel = NotificationChannel(channelID, "default", NotificationManager.IMPORTANCE_HIGH)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        val notification = Notification.Builder(this, channelID).apply {
            setContentTitle("Notification Title")
            setContentText("This is Content")
            setSmallIcon(R.drawable.ic_launcher_background)
            setOngoing(true)
        }.build()
        startForeground(SEDRVICE_ID, notification)
    }
}