package com.jhmk.whereami.Module.Main

import android.util.Log

class MainPresenter : Main.presenter {
    private val TAG = this.javaClass.simpleName

    override fun onDestroy() {
        Log.d(TAG, "##### onDEstroy #####")
    }
}