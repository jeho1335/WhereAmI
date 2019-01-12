package com.jhmk.whereami.Module.Home

import android.content.Context
import com.jhmk.whereami.Module.Base.Base
import com.jhmk.whereami.Utils.Location.GeoPoint

interface Home {
    interface view {
        fun onResultMessage(msg : String)
        fun onResultChangeProgressMessage(msg : String)
        fun onResultCurrentLocation(geoPoint: GeoPoint, stLine : String)
        fun onResultNearbyStation(isSuccess: Boolean, stName: String?, stLine: String?)
        fun onResultPrevNextStation(isSuccess: Boolean, prevName: String?, nextName: String?)
    }

    interface presenter : Base {
        fun requestCurrentLocation(context : Context, stLine : String)
        fun requestNearbyStation(context: Context, line: String?, geoPoint: GeoPoint)
    }
}