package com.example.claculater.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.claculater.data.App
import com.example.claculater.data.AppInfo

class AppDiffUtil(val oldList:List<AppInfo>, val newList:List<AppInfo>):DiffUtil.Callback(){
    override fun getOldListSize()= oldList.size

    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) :Boolean{
        Log.i("OOOL","${oldList[oldList.size-1].appName == newList[newList.size-1].appName}")
        return oldList[oldItemPosition].packageName==newList[newItemPosition].packageName && oldList[oldItemPosition].appName==newList[newItemPosition].appName && oldList[oldItemPosition].isLocked==newList[newItemPosition].isLocked
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)= true
}