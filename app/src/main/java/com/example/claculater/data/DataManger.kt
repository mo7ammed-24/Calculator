package com.example.claculater.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.example.claculater.data.DataManger.appList

object DataManger {
    var userPassword:Int? = null
    private var appList = mutableListOf<AppInfo>()
    val apps  : List<AppInfo>
        get() = appList.toList()
    
    
    fun addApp(newApp:AppInfo){
        appList.add(newApp)
        Log.i("HH",appList.toString())
    }

    fun setAppsData(context:Context){
        Log.i("JJJJ", context.toString())
        appList = getAllApps(context).toMutableList()
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


}