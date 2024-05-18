package com.example.claculater.data

import android.util.Log
import com.example.claculater.data.DataManger.appList

object DataManger {
    var userPassword:Int? = null
    private val appList = mutableListOf(App("Pinterest", true, "Picture"),
    App("Behance", true, "Picture"),
    App("Facebook", true, "Picture"),
    App("YouTube", true, "Picture"),
    App("LinkedIn", true, "Picture"),
    App("Messenger", true, "Picture"),
    App("StackOverflow", true, "Picture"),
    App("Instagram", true, "Picture"),
    )
    val apps  : List<App>
        get() = appList.toList()
    
    
    fun addApp(newApp:App){
        appList.add(newApp)
        Log.i("HH",appList.toString())
    }
    

    
}