package com.example.claculater.ui.main.activities

import android.content.Intent
import android.view.LayoutInflater
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityLockBinding
import com.itsxtt.patternlock.PatternLockView
import kotlin.collections.ArrayList

class LockActivity:BaseActivity<ActivityLockBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityLockBinding = ActivityLockBinding::inflate
    var isLocked= false

    override fun initialize() {
        checkingLockPattern()
    }

    override fun callBacks() {
        //
    }

    private fun checkingLockPattern() {
        binding.patternLock.setOnPatternListener(object :PatternLockView.OnPatternListener{
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                var str = ""
                ids.forEach {
                    str+=it.toString()
                }
                isLocked = str == "012"
                return str == "012"
            }
        })
    }

}