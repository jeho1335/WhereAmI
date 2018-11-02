package com.jhmk.whereami.Module.Home

import android.content.Context
import com.jhmk.whereami.Module.Base.Base

interface Home {
    interface view {
        fun onResultCurrentLocation(isSuccess: Boolean)
        fun onResultNearbyStation(isSuccess : Boolean, stName: String?, stLine : String?)
        fun onResultPrevNextStation(isSuccess : Boolean, prevName : String?, nextName : String?)
    }

    interface presenter : Base{
        fun requestNearbyStation(context: Context, line : String?)
    }
}
