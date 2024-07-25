package com.example.claculater.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.claculater.interfaces.OnHomePressedListener


class HomeWatcher(private val context: Context) {

    companion object { const val TAG = "hg"
    }

    private var mFilter: IntentFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
    private var mListener: OnHomePressedListener? = null
    private var mReceiver: InnerReceiver? = null

    fun setOnHomePressedListener(listener: OnHomePressedListener) {
        mListener = listener
        mReceiver = InnerReceiver()
    }

    fun startWatch() {
        mReceiver?.let { context.registerReceiver(it, mFilter) }
    }

    fun stopWatch() {
        mReceiver?.let { context.unregisterReceiver(it) }
    }

    inner class InnerReceiver : BroadcastReceiver() {
        val SYSTEM_DIALOG_REASON_KEY = "reason";
        val SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        val SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        val SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                val reason = intent?.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            mListener!!.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            mListener!!.onRecentAppsPressed();
                        }
                    }
                }
            }
        }
    }
}