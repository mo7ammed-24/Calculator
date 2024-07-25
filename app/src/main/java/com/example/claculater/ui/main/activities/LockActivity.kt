package com.example.claculater.ui.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.data.DataManger
import com.example.claculater.databinding.ActivityLockBinding
import com.example.claculater.interfaces.OnHomePressedListener
import com.example.claculater.util.HomeWatcher
import com.itsxtt.patternlock.PatternLockView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class LockActivity:BaseActivity<ActivityLockBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityLockBinding = ActivityLockBinding::inflate
    var isOpened= false
    val mHomeWatcher = HomeWatcher(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = this@LockActivity.getSharedPreferences(DataManger.PATTERN_SHARING, Context.MODE_PRIVATE)
        DataManger.patterCreated = sharedPrefs.getBoolean(DataManger.PATTERN_CREATED_KEY, false)
        DataManger.LOCK_PASSWORD = sharedPrefs.getString(DataManger.LOCK_PASSWORD_KEY, "")!!

    }

    override fun initialize() {
        mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {override fun onHomePressed() {
            isOpened=true
            finishAndRemoveTask()
        }

            override fun onRecentAppsPressed() {
                isOpened=true
                finishAndRemoveTask()
            }
        })
        mHomeWatcher.startWatch()
        checkingLockPattern()
    }

    override fun callBacks() {
        //
    }

    private fun checkingLockPattern() {
        binding.patternLock.setOnPatternListener(object :PatternLockView.OnPatternListener{
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                var str:String? = null
                    ids.forEach {
                        str+=it.toString()
                    }
                    if (str == DataManger.LOCK_PASSWORD){
                        isOpened=true
                        finishAndRemoveTask()
                    }
                return str == DataManger.LOCK_PASSWORD
            }
        })
    }

    override fun onStop() {
        super.onStop()
        if (isOpened==false){ startActivity(Intent(this, LockActivity::class.java))
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
                KeyEvent.KEYCODE_BACK ->{
                    Log.i("pppp", " Back")
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_HOME)
                    }
                    isOpened=true
                    startActivity(intent)
                    finishAndRemoveTask()
                    return true
                }
        }
        return super.onKeyDown(keyCode, event)
    }

}