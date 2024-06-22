package com.example.claculater.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.example.claculater.base.BaseActivity
import com.example.claculater.databinding.ActivityLockBinding
import com.itsxtt.patternlock.PatternLockView
import kotlin.collections.ArrayList

class LockActivity:BaseActivity<ActivityLockBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityLockBinding = ActivityLockBinding::inflate
    var isOpened= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("hggg", "i am in the create lifecycle")
    }

    override fun onStart() {
        super.onStart()
        Log.i("hggg", "i am in the start lifecycle")
    }
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
                if (str == "012"){
                    isOpened=true
                    finish()
                isOpened=false}
                return str == "012"
            }
        })

    }

    var dispatcher = onBackPressedDispatcher.addCallback(onBackPressedCallback = object :OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
            }
            startActivity(intent)
            finishAndRemoveTask()
        }

    })

    override fun onPause() {
        finishAndRemoveTask()
        super.onPause()
    }



}