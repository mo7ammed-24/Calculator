package com.example.claculater.ui.main.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager.widget.ViewPager
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityHomeBinding
import com.example.claculater.ui.main.adapters.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
        binding.tabs.setTabTextColors(getColor(R.color.white), getColor(R.color.white))

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