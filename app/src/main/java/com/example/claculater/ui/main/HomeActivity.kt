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
import com.example.claculater.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab
        tabs.setTabTextColors(getColor(R.color.white), getColor(R.color.brand))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("fff", "onPause2")
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("www", "this on restart 2")
    }

    override fun initialize() {
        //not written yet
    }

    override fun callBacks() {
        //not written yet
    }
}