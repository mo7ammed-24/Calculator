package com.example.claculater.data

import android.R.attr
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object DataManger {
    val NOT_LOCKED_APPS = "not_locked_apps"
    val LOCKED_APPS = "locked_apps"
    val SHARIN_APPS_INFO = "sharing_apps_info"

    val gson = Gson()
    var notLockedApps = mutableListOf<AppInfo>()
    var lockedApps = mutableListOf<AppInfo>()
    var userPassword:Int? = null
    private var appList = mutableListOf<AppInfo>()
    val apps  : List<AppInfo>
        get() = appList.toList()
    val lockedAppsPackageNames : List<String>
        get() = lockedApps.map{it.packageName}

    fun addApp(newApp:AppInfo){
        appList.add(newApp)
        Log.i("HH",appList.toString())
    }

    fun setAppsData(context:Context){
        Log.i("JJJJ", context.toString())
        appList = getAllApps(context).toMutableList()
        notLockedApps = getNotLockedApps(NOT_LOCKED_APPS,context)
        lockedApps = getLockedApps(LOCKED_APPS,context)
    }


    fun getAllApps(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val userApps = installedApps.filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
        val appList = mutableListOf<AppInfo>()

        for (appInfo in userApps) {
            val appName = appInfo.loadLabel(packageManager).toString()
            val packageName = appInfo.packageName
            val icon = appInfo.loadIcon(packageManager)
            appList.add(AppInfo(appName, packageName, icon))
        }
        return appList
    }

    fun getNotLockedApps(key:String, context: Context): ArrayList<AppInfo> {
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val json: String = preferences.getString(key, "").toString()
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AppInfo?>?>() {}.type
        val switchGroup1: ArrayList<AppInfo> = gson.fromJson(json, type)
        return switchGroup1
    }

    fun getLockedApps(key:String, context: Context): ArrayList<AppInfo> {
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val json: String = preferences.getString(key, "").toString()
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AppInfo>?>() {}.type
        val switchGroup1: ArrayList<AppInfo> = gson.fromJson(json, type)
        return switchGroup1
    }


    fun lockeTheApp(app:AppInfo){
        lockedApps.add(app)
        appList.forEach {
            if (it.packageName==app.packageName)
                it.isLocked=true
        }
    }

    fun openTheApp(app: AppInfo){
        notLockedApps.add(app)
        appList.forEach {
            if (it.packageName==app.packageName)
                it.isLocked=false
        }
    }

    fun saveAppsInfo(context:Context){
        // Initialize SharedPreferences
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val notLockedAppsJSON = gson.toJson(notLockedApps)
        val lockedAppsJSON = gson.toJson(lockedApps)
        val editor = preferences.edit()
        //Add data to SharedPreferences
        editor.putString(NOT_LOCKED_APPS, notLockedAppsJSON)
        editor.putString(LOCKED_APPS, lockedAppsJSON)
        editor.apply() // Use apply() instead of commit() for asynchronous saving
    }

}