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
    val PATTERN_SHARING = "pattern_sharing"
    val PATTERN_CREATED = "pattern_created"
    var LOCK_PASSWORD = ""

    val gson = Gson()


    private var _notLockedApps = mutableListOf<AppInfo>()
    val notLockedApps : List<AppInfo>
        get() = _notLockedApps.toList()

    private var _lockedApps = mutableListOf<AppInfo>()
    val lockedApps :List<AppInfo>
        get() = _lockedApps.toList()


    var userPassword:Int? = null
    var patterCreated  = false
    private var appList = mutableListOf<AppInfo>()
    val apps  : List<AppInfo>
        get() = appList.toList()


    val lockedAppsPackageNames : List<String>
        get() = _lockedApps.map{it.packageName}

    fun addApp(newApp:AppInfo){
        appList.add(newApp)
        Log.i("HH",appList.toString())
    }

    fun setAppsData(context:Context){
        Log.i("JJJJ", context.toString())
        appList = getAllApps(context).toMutableList().sortedBy { it.appName }.toMutableList()
        _notLockedApps = getNotLockedApps(NOT_LOCKED_APPS,context).sortedBy { it.appName }.toMutableList()
        _lockedApps = getLockedApps(LOCKED_APPS,context).sortedBy { it.appName }.toMutableList()
        Log.i("locked", _lockedApps.toString())
    }

    fun getAllApps(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val userApps = installedApps.filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
        val appList = mutableListOf<AppInfo>()

        for (appInfo in userApps) {
            val appName = appInfo.loadLabel(packageManager).toString()
            val packageName = appInfo.packageName
            val iconResId = appInfo.icon
            appList.add(AppInfo(appName, packageName, iconResId))
        }
        return appList
    }

    fun getNotLockedApps(key:String, context: Context): MutableList<AppInfo> {
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val json: String = preferences.getString(key, "").toString()
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AppInfo?>?>() {}.type
        val switchGroup1: ArrayList<AppInfo>? = gson.fromJson(json, type)
        switchGroup1?.toMutableList()

        if (switchGroup1==null)
            return appList

        appList.forEach {
            if (it !in switchGroup1 && it !in _notLockedApps && it.isLocked==false){
                it.isLocked = true
                if (it !in _lockedApps){
                    it.isLocked=false
                    switchGroup1.add(it)
                }
            }

        }

        return switchGroup1
    }

    fun getLockedApps(key:String, context: Context): MutableList<AppInfo> {
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val json: String = preferences.getString(key, "").toString()
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AppInfo>?>() {}.type
        val switchGroup1: ArrayList<AppInfo>? = gson.fromJson(json, type)
        if (switchGroup1==null)
            return mutableListOf()
        else
            return switchGroup1
    }


    fun lockeTheApp(app:AppInfo){
        _notLockedApps.remove(app)
        Log.i("ifghjdjf", _notLockedApps.remove(app).toString())
        val ss = app
        ss.isLocked = true
        if (ss !in _lockedApps)
            _lockedApps.add(ss)
    }

    fun openTheApp(app: AppInfo){
        _lockedApps.remove(app)
        val ss =app
        ss.isLocked = false
        if (ss !in _notLockedApps)
            _notLockedApps.add(ss)
    }

    fun saveAppsInfo(context:Context){
        // Initialize SharedPreferences
        val preferences = context.getSharedPreferences(SHARIN_APPS_INFO, Context.MODE_PRIVATE)
        val notLockedAppsJSON = gson.toJson(_notLockedApps)
        val lockedAppsJSON = gson.toJson(_lockedApps)
        val editor = preferences.edit()
        //Add data to SharedPreferences
        editor.putString(NOT_LOCKED_APPS, notLockedAppsJSON)
        editor.putString(LOCKED_APPS, lockedAppsJSON)
        editor.apply() // Use apply() instead of commit() for asynchronous saving
    }

}