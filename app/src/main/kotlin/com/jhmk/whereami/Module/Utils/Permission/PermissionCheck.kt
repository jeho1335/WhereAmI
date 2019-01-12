package com.jhmk.whereami.Module.Utils.Permission

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.tedpark.tedpermission.rx2.TedRx2Permission


class PermissionCheck {
    private val TAG = this.javaClass.simpleName

    fun checkPermission(context: Context, permission: String, result: (Boolean) -> Unit){
        Log.d(TAG, "##### checkPermission #####")
        TedRx2Permission
            .with(context)
            .setPermissions(permission)
            .request()
            .subscribe({ tedPermissionResult ->
                result(tedPermissionResult.isGranted)
            }) { throwable ->

            }
    }
}