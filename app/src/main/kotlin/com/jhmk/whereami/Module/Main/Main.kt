package com.jhmk.whereami.Module.Main

import android.content.Context

interface Main {
    interface view{
        fun onResultRequestCheckPermission(msg : String)
    }
    interface presenter{
        fun checkNetworkThreadPolicy()
        fun requestCheckPermission(context : Context, permission : String)
    }
}