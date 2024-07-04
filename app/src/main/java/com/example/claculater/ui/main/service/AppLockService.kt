package com.example.claculater.ui.main.service

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.rotationMatrix
import com.example.claculater.R
import com.example.claculater.data.DataManger
import com.example.claculater.ui.main.activities.LockActivity

class AppLockService:Service() {
    private var runningAppPackageName :String?= ""

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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        showMessage()
        var currentAppPackage =""
        val handler = Handler(Looper.getMainLooper())
        val runnable = object :Runnable {
            override fun run() {
                val usageStatsManager =
                    getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                val endTime = System.currentTimeMillis()
                val beginTime = endTime - 1000*60 // Look back for 1 hour
                val stats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY,
                    beginTime,
                    endTime
                )
                // Find the foreground app based on last active time
                val foregroundApp = stats.maxByOrNull { it.lastTimeUsed }?.packageName
                Log.i("hhhfff", foregroundApp.toString())
                if (foregroundApp in DataManger.lockedAppsPackageNames && currentAppPackage!=foregroundApp && currentAppPackage!=applicationContext.packageName) {
                    val intent = Intent(this@AppLockService, LockActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    currentAppPackage = foregroundApp.toString()
                    startActivity(intent)
                }
                else
                    currentAppPackage= foregroundApp.toString()
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnable)
//        Log.i("fxxxfff", foregroundApp.toString())
        return super.onStartCommand(intent, flags, startId)
    }

//    private fun showMessage() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            Toast.makeText(applicationContext, " Hi this is Service ", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this,LockActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        },5000)
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(){
        val channelID = "my_channel_1"
        val channel = NotificationChannel(channelID, "default", NotificationManager.IMPORTANCE_HIGH)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        val notification = Notification.Builder(this, channelID).apply {
            setContentTitle("Calculator")
            setContentText("Have A Good Day")
            setSmallIcon(R.drawable.calculator)
            setOngoing(true)
        }.build()
        startForeground(SEDRVICE_ID, notification)

        }



}