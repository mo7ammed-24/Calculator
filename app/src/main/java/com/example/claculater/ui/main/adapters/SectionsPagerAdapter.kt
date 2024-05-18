package com.example.claculater.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(container: FragmentActivity, private val fragmentList: List<Fragment>):
    FragmentStateAdapter(container){
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

}