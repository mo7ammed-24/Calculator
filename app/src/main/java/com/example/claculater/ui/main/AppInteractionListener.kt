package com.example.claculater.ui.main

import com.example.claculater.data.App

interface AppInteractionListener {
     fun onClickItem(app:App)
     fun onSwitchLock()

}