package com.jhmk.whereami.Module.Utils

import android.util.Log
import com.jhmk.whereami.Model.StationInfo
import com.jhmk.whereami.Model.StationList

@Suppress("UNREACHABLE_CODE")
class StationFilter {
    val TAG = this.javaClass.simpleName

    fun filterStationByLine(stList: StationList, stLine: String?): StationInfo? {
        var isOnTheCurrentLine = false
        for ((index, value) in stList.stationList!!.withIndex()) {
            Log.d(TAG, "##### RX_RESPONSE_NEARBY_STATIONS LOOP ${value.subwayNm}  ${value.statnNm}")
            if (value.subwayNm == stLine) {
                return value
                isOnTheCurrentLine = true
                break
            }
        }
        if (!isOnTheCurrentLine) {
            return stList.stationList?.get(0)
        }
        return null
    }
}