package com.example.claculater.ui.main.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.claculater.R
import com.example.claculater.databinding.ItemAppBinding

class AppViewHolder(var viewItem:View):RecyclerView.ViewHolder(viewItem) {
    val binding = ItemAppBinding.bind(viewItem)
            val appTextName = binding.appName
}