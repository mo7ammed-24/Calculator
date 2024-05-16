package com.example.claculater.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.claculater.R
import com.example.claculater.data.App
import com.example.claculater.ui.main.viewHolders.AppViewHolder

class AppAdapter(val list:List<App>):RecyclerView.Adapter<AppViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val currentApp = list[position]
        holder.binding.apply {  appName.text = currentApp.appName}
    }
}