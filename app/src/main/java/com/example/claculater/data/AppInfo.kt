package com.example.claculater.data

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon

data class AppInfo(val appName:String, val packageName: String, val iconResId: Int, var isLocked :Boolean = false, var switchFlag: Boolean=false)
