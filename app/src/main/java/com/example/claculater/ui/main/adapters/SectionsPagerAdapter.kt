package com.example.claculater.ui.main.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.claculater.R
import com.example.claculater.ui.main.fragments.PlaceholderFragment
import kotlinx.coroutines.withContext

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(val context: Context, container: FragmentManager):
    FragmentPagerAdapter(container){

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = 2

    override fun getItem(position: Int) =PlaceholderFragment.newInstance(position+1)

}