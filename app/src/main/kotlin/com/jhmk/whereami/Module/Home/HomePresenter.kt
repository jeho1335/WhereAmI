package com.jhmk.whereami.Module.Home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.iriver.constraintsample.Model.Utils.Location.GpsClient
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Model.CurrentLineManager
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Model.StationList
import com.jhmk.whereami.Module.Utils.Network.ApiClient
import com.jhmk.whereami.Module.Utils.StationFilter
import com.jhmk.whereami.Utils.Location.GeoPoint

class HomePresenter(view: Home.view) : Home.presenter {
    private val TAG = this.javaClass.simpleName
    private val mView = view
    private lateinit var mCurrentLine: String

    init {
        RxEvent.getObservable().subscribe({
            when (it.key) {
                ConstVariables.RX_RESPONSE_NEARBY_STATIONS -> {
                    if (it.value is StationList) {
                        val curInfo = StationFilter().filterStationByLine(it.value, mCurrentLine)
                        mView.onResultNearbyStation(true, curInfo?.statnNm, curInfo?.subwayNm)
                        ApiClient().getPrevNextStations(curInfo?.statnNm)
                    } else {
                        mView.onResultNearbyStation(false, null, null)
                    }
                }
                ConstVariables.RX_RESPONSE_PREVNEXT_STATIONS -> {
                    if (it.value is StationList) {
                        val curInfo = StationFilter().filterStationByLine(it.value, mCurrentLine)
                        mView.onResultPrevNextStation(true, curInfo?.statnTnm as String, curInfo.statnFnm as String)
                    } else {
                        mView.onResultPrevNextStation(false, null, null)
                    }
                }
                ConstVariables.RX_RESPONSE_CURRENT_LOCATION -> {
                    if (it.value is GeoPoint) {
                        ApiClient().getNearbyStation(it.value.x.toString(), it.value.y.toString())
                    } else {
                        mView.onResultCurrentLocation(false)
                    }
                }
            }
        }, {
            Log.d(TAG, "onCreate: onError()")
            mView.onResultNearbyStation(false, null, null)
        }, {
            Log.d(TAG, "onCreate: onCompleted()")
            mView.onResultNearbyStation(false, null, null)
        })
    }

    override fun onDestroy() {
        Log.d(TAG, "##### onDestroy #####")
        RxEvent.getObservable().subscribe().dispose()
    }

    override fun requestNearbyStation(context: Context, line: String?) {
        Log.d(TAG, "##### requestNearbyStation #####")

        val lineList = CurrentLineManager(context).lineInfoList
        for ((index, value) in lineList.withIndex()) {
            if (value.name == line) {
                mCurrentLine = lineList[index].name
                break
            }
        }
        GpsClient().getCurrentLocation(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }
}