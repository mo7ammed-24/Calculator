package com.example.claculater.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityHomeBinding
import com.example.claculater.ui.main.adapters.SectionsPagerAdapter
import com.example.claculater.ui.main.fragments.PlaceholderFragment
import com.example.claculater.ui.main.service.AppLockService
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