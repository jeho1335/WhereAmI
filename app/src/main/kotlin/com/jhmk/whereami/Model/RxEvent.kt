package com.jhmk.whereami.Model

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxEvent {
    private val TAG = this.javaClass.simpleName
    private var mSubject: PublishSubject<RxEventObject> = PublishSubject.create()
    fun sendEvent(obj: RxEventObject) {
        Log.d(TAG, "##### sendEvent #####")
        mSubject.onNext(obj)
//        mSubject.onComplete()
    }

    fun getObservable(): Observable<RxEventObject> {
        Log.d(TAG, "##### getObservable #####")
        return mSubject
    }
}