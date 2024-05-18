package com.example.claculater.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.data.App
import com.example.claculater.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate
    val tabTitles = listOf("Locked Apps", "Unlocked Apps")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, listOf(PlaceholderFragment(), PlaceholderFragment()))
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position->
            tab.text = tabTitles[position]
        }.attach()
        binding.tabs.setTabTextColors(getColor(R.color.white), getColor(R.color.brand))

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun initialize() {
        //not written yet
    }

    override fun callBacks() {
    }
}