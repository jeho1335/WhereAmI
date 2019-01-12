package com.jhmk.whereami.Module.Base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity(){
    val disposable by lazy {
        CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}