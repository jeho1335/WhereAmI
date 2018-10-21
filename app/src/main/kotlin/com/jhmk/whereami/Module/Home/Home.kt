package com.jhmk.whereami.Module.Home

import android.content.Context
import com.jhmk.whereami.Model.SelectLineButtonInfo
import com.jhmk.whereami.Module.Base.Base

interface Home {
    interface view : Base {
        fun onResultNearbyStation(isSuccess : Boolean, result: String?)
    }

    interface presenter {
        fun requestNearbyStation(context: Context)
    }
}