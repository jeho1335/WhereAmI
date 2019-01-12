package com.jhmk.whereami.Module.Main

import android.Manifest
import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.jhmk.whereami.R
import com.tedpark.tedpermission.rx2.TedRx2Permission

class MainPresenter(val view : MainActivity) : Main.presenter {
    private val TAG = this.javaClass.simpleName
    val mView = view as Main.view

    override fun checkNetworkThreadPolicy() {
        Log.d(TAG, "##### checkNetworkThreadPolicy #####")
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy.Builder().permitAll().build().let {
                StrictMode.setThreadPolicy(it)
            }
        }
    }

    override fun requestCheckPermission(context: Context, permission: String) {
        Log.d(TAG, "##### requestCheckPermission #####")
        TedRx2Permission
            .with(context)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .request()
            .subscribe({ permissionResult ->
                if(permissionResult.isGranted){
                    Log.d(TAG, "##### requestCheckPermission ##### $permission is granted")

                }else{
                    Log.d(TAG, "##### requestCheckPermission ##### $permission is not granted")
                    mView.onResultRequestCheckPermission(view.getString(R.string.string_request_gps_location_denied))
                }
            }, {throwable ->
                throwable.printStackTrace()
            }).apply {
                view.disposable.add(this)
            }
    }
}