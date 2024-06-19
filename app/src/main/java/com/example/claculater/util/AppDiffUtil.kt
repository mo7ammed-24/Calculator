package com.example.claculater.util

import androidx.recyclerview.widget.DiffUtil
import com.example.claculater.data.App
import com.example.claculater.data.AppInfo

class AppDiffUtil(val oldList:List<AppInfo>, val newList:List<AppInfo>):DiffUtil.Callback(){
    override fun getOldListSize()= oldList.size

    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)= oldList[oldItemPosition].appName==newList[newItemPosition].appName

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)=true
}