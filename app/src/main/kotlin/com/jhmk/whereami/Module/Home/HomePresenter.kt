package com.jhmk.whereami.Module.Home

import android.Manifest
import android.content.Context
import android.util.Log
import com.example.iriver.constraintsample.Model.Utils.Location.GpsClient
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Model.UserStations
import com.jhmk.whereami.Model.Utils.Network.ApiClient
import com.jhmk.whereami.Module.Base.Base
import com.jhmk.whereami.Utils.Location.GeoPoint

class HomePresenter(view: Home.view) : Home.presenter, Base {
    private val TAG = this.javaClass.simpleName
    private val mView = view

    init {
        RxEvent.getObservable().subscribe({
            when (it.key) {
                ConstVariables.RX_RESPONSE_NEARBY_STATIONS -> {
                    if (it.value is UserStations) {
                        for ((index, value) in it.value.stationList!!.withIndex()) {
                            if (it.value.stationList != null) {
                                Log.d(TAG, "##### RxEvent subscribe ##### ${value.subwayNm} ${value.statnNm} 역")
                            } else {
                                mView.onResultNearbyStation(false, null)
                            }
                        }
                        mView.onResultNearbyStation(true, it.value.stationList?.get(0)?.statnNm + "역")
                    } else {
                        mView.onResultNearbyStation(false, null)
                    }
                }
                ConstVariables.RX_RESPONSE_CURRENT_LOCATION -> {
                    if (it.value is GeoPoint) {
                        Log.d(TAG, "##### RxEvent subscribe ##### ${it.value.x} ${it.value.y}")
                        ApiClient().getNearbyStation(it.value.x.toString(), it.value.y.toString())
                    } else {
                        mView.onResultNearbyStation(false, null)
                    }
                }
            }
        }, {
            Log.d(TAG, "onCreate: onError()")
            mView.onResultNearbyStation(false, null)
        }, {
            Log.d(TAG, "onCreate: onCompleted()")
            mView.onResultNearbyStation(false, null)
        })
    }

    override fun onDestroy() {
        Log.d(TAG, "##### onDestroy #####")
        RxEvent.getObservable().subscribe().dispose()
    }

    override fun requestNearbyStation(context: Context) {
        Log.d(TAG, "##### requestCount #####")
        GpsClient().getCurrentLocation(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }

}