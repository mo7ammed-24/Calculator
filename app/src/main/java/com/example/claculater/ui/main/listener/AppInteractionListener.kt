package com.example.claculater.ui.main.listener

import com.example.claculater.data.App
import com.example.claculater.data.AppInfo

interface AppInteractionListener {
     fun onClickItem(app:AppInfo)
     fun onSwitchLock()

}