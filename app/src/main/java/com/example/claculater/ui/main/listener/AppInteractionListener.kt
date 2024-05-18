package com.example.claculater.ui.main.listener

import com.example.claculater.data.App

interface AppInteractionListener {
     fun onClickItem(app:App)
     fun onSwitchLock()

}