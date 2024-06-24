package com.example.claculater.ui.main.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.claculater.R
import com.example.claculater.data.App
import com.example.claculater.data.AppInfo
import com.example.claculater.util.AppDiffUtil
import com.example.claculater.ui.main.listener.AppInteractionListener
import com.example.claculater.ui.main.viewHolders.AppViewHolder

class AppAdapter(private var list:List<AppInfo>, private val listener: AppInteractionListener):RecyclerView.Adapter<AppViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun getItemCount()=list.size

    fun setData(newList:List<AppInfo>){
        Log.i("Mohammed", "${newList.find{ it.packageName =="com.whatsapp.w4b" }?.appName}  is ${newList.find{ it.packageName=="com.whatsapp.w4b" }?.isLocked}")
        val diffResult = DiffUtil.calculateDiff(AppDiffUtil(list, newList))
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val currentApp = list[position]
        Log.i("gghhg", "${list.get(0).appName} it is switch ${list.get(0).isLocked}" )
        holder.binding.apply {
            appName.text = currentApp.appName
        root.setOnClickListener { listener.onClickItem(currentApp)}
        switchLock.setOnCheckedChangeListener{switch , isLocked ->
                listener.onSwitchLock(currentApp, isLocked)
        }
            switchLock.isChecked=currentApp.isLocked
        }

        Glide.with(holder.binding.root).load(currentApp.icon).centerCrop().into(holder.binding.appImage)
    }
}