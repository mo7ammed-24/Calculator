package com.example.claculater.ui.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.example.claculater.R
import com.example.claculater.base.BaseActivity
import com.example.claculater.data.DataManger
import com.example.claculater.databinding.ActivityLockBinding
import com.itsxtt.patternlock.PatternLockView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class LockActivity:BaseActivity<ActivityLockBinding>(){
    override val bindingInflater: (LayoutInflater) -> ActivityLockBinding = ActivityLockBinding::inflate
    var isOpened= false
    var argument:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("hggg", "i am in the create lifecycle")

        val sharedPrefs = this@LockActivity.getSharedPreferences(DataManger.PATTERN_SHARING, Context.MODE_PRIVATE)
        DataManger.patterCreated = sharedPrefs.getBoolean(DataManger.PATTERN_CREATED_KEY, false)
        DataManger.LOCK_PASSWORD = sharedPrefs.getString(DataManger.LOCK_PASSWORD_KEY, "")!!

        argument= intent.getIntExtra("args",0)
        lifecycleScope.launch {
            val text = withContext(Dispatchers.IO) {
                getString(R.string.draw_your_pattern)
            }
            val text2 = withContext(Dispatchers.IO){
                getString(R.string.redraw_your_pattern)
            }

            // Set the text on the main thread
            withContext(Dispatchers.Main) {
                if (argument==1){
                    binding.textPattern.text = text
                }
                else if(argument==2){binding.textPattern.text = text2}
            }
        }


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
                var str:String? = null

                if (argument==1){
                    ids.forEach {
                        str+=it.toString()
                    }
                    val intent = Intent(this@LockActivity, LockActivity::class.java)
                    intent.putExtra("args", 2)
                    intent.putExtra("args2", str)
                    startActivity(intent)
                    finish()
                }
                else if(argument==2){
                    var argument2 = intent.getStringExtra("args2")
                    ids.forEach {
                        str+=it.toString()
                    }

                    if (str==argument2){
                        DataManger.patterCreated = true
                        val sharedPrefs = this@LockActivity.getSharedPreferences(DataManger.PATTERN_SHARING, Context.MODE_PRIVATE)
                        val editor = sharedPrefs.edit()
                        editor.putBoolean(DataManger.PATTERN_CREATED_KEY, DataManger.patterCreated!!)
                        editor.putString(DataManger.LOCK_PASSWORD_KEY, str!!)
                        editor.apply()
                        DataManger.LOCK_PASSWORD = str!!
                        finish()
                        val intent = Intent(this@LockActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                else{
                    ids.forEach {
                        str+=it.toString()
                    }
                    Log.i("sssd", "this: ${DataManger.LOCK_PASSWORD}")
                    if (str == DataManger.LOCK_PASSWORD){
                        isOpened=true
                        finishAndRemoveTask()
                        isOpened=false
                    }
                }
                return str == DataManger.LOCK_PASSWORD
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