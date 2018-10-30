package com.jhmk.whereami.Module.Utils.Permission

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.tedpark.tedpermission.rx2.TedRx2Permission


class PermissionCheck {
    private val TAG = this.javaClass.simpleName
    interface onCheckedListener{
        fun onChecked(result : Boolean)
    }
    @SuppressLint("CheckResult")
    fun checkPermission(context : Context, permission : String, listener : onCheckedListener){
        Log.d(TAG, "##### checkPermission #####")
        TedRx2Permission.with(context)
                .setPermissions(permission)
                .request()
                .subscribe({ tedPermissionResult ->
                    if(tedPermissionResult.isGranted){
                        listener.onChecked(true)
                    }else{
                        listener.onChecked(false)
                    }
                }) { throwable -> }
    }
}