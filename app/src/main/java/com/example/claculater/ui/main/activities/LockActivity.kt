package com.example.claculater.ui.main.activities

import android.content.Intent
import android.view.LayoutInflater
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityLockBinding
import com.itsxtt.patternlock.PatternLockView
import kotlin.collections.ArrayList

class LockActivity:BaseActivity<ActivityLockBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityLockBinding = ActivityLockBinding::inflate

    override fun initialize() {
        checkingLockPattern()
    }



    override fun callBacks() {
        //
    }

    fun checkingLockPattern() {
        binding.patternLock.setOnPatternListener(object :PatternLockView.OnPatternListener{
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                val list = ArrayList<Int>()
                list.add(1)
                list.add(2)
                list.add(3)
                var str = ""
                ids.forEach {
                    str+=it.toString()
                }
                return str == "012"
            }
        })
    }
}