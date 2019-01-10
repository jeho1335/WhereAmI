package com.jhmk.whereami.Module.Base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    open val disposables by lazy {
        CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.disposables.clear()
    }
}