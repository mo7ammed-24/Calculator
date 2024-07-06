package com.example.claculater.ui.main.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import androidx.viewpager.widget.ViewPager
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityHomeBinding
import com.example.claculater.ui.main.adapters.SectionsPagerAdapter
import com.example.claculater.ui.main.fragments.PlaceholderFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
        binding.tabs.setTabTextColors(getColor(R.color.white), getColor(R.color.white))

        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                ///ddd
            }

            override fun onPageSelected(position: Int) {
                val current = supportFragmentManager.fragments[position]
                    if (current is PlaceholderFragment)
                        current.onTabChanged(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                ///dddd
            }

        })
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