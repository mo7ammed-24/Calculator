package com.example.claculater.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.claculater.data.App

class AppDiffUtil(val oldList:List<App>, val newList:List<App>):DiffUtil.Callback(){
    override fun getOldListSize()= oldList.size

    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)= oldList[oldItemPosition].appName==newList[newItemPosition].appName

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)=true
}