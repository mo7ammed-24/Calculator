package com.example.claculater.ui.main.adapters

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
        Log.i("Mohammed", "$newList")
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
                if(isLocked != currentApp.isLocked)
                    listener.onSwitchLock(currentApp, isLocked)
        }
            switchLock.isChecked=currentApp.isLocked

        }

        val iconDrawable = getAppIcon(holder.binding.root.context, currentApp.packageName)
        Glide.with(holder.binding.root).load(iconDrawable).centerCrop().into(holder.binding.appImage)
    }

    fun getAppIcon(context: Context, packageName: String): Drawable? {
        return try {
            val packageManager = context.packageManager
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            appInfo.loadIcon(packageManager)
        } catch (e: PackageManager.NameNotFoundException) {
            //Handle the case where the package is not found
            null
        }
    }
}