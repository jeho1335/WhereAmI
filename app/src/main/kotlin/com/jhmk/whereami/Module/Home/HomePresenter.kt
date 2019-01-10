package com.jhmk.whereami.Module.Home

import android.Manifest
import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.example.iriver.constraintsample.Model.Utils.Location.GpsClient
import com.jhmk.whereami.Model.CurrentLineManager
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Module.Utils.Network.ApiClient
import com.jhmk.whereami.Module.Utils.Permission.PermissionCheck
import com.jhmk.whereami.Module.Utils.StationFilter
import com.jhmk.whereami.Utils.Location.GeoPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomePresenter(val view: HomeFragment) : Home.presenter {
    private val TAG = this.javaClass.simpleName
    private val mView = view as Home.view
    private lateinit var mCurrentLine: String

    override fun onDestroy() {
        Log.d(TAG, "##### onDestroy #####")
        RxEvent.getObservable().subscribe().dispose()
    }

    override fun requestNearbyStation(context: Context, line: String?) {
        Log.d(TAG, "##### requestNearbyStation #####")

        val lineList = CurrentLineManager(context).lineInfoList
        for ((anything, isokay) in lineList.withIndex()) {
            if (isokay.name == line) {
                mCurrentLine = lineList[anything].name
                break
            }
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        PermissionCheck().checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION, object : PermissionCheck.onCheckedListener {
                override fun onChecked(permissionCheckResult: Boolean) {
                    when (permissionCheckResult) {
                        true -> {
                            getCurrentLocation()
                        }
                        false -> {
                            //권한 요청
                        }
                    }
                }
            })
    }

    private fun getCurrentLocation() {
        Log.d(TAG, "##### getCurrentLcoation #####")
        GpsClient().getCurrentLocation(view.context) { currentLocation ->
            getNearByStation(currentLocation)
        }
    }

    private fun getNearByStation(currentLocation: GeoPoint) {
        Log.d(TAG, "##### getNearByStation #####")
        ApiClient().getRetrofitClient().create(ApiClient.ApiService::class.java).run {
            this.getNearbyStations(currentLocation.x.toString(), currentLocation.y.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { stations ->
                    stations.stationList?.get(0)?.statnNm ?: ""
                }
                .filter { stationName ->
                    (stationName != "").also {
                        if(it){
                            mView.onResultNearbyStation(true, stationName, "")
                        }
                    }
                }
                .concatMap { stationName ->
                    ApiClient()
                        .getRetrofitClient().create(ApiClient.ApiService::class.java).run {
                            this.getPrevNextStations(stationName)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
                .map { stations ->
                    StationFilter().filterStationByLine(stations, mCurrentLine)
                }
                .subscribe({ stationInfo ->
                    Log.d(TAG, "##### onNext #####")
                        mView.onResultPrevNextStation(true, stationInfo?.statnTnm as String, stationInfo.statnFnm as String)
                }, { throwable ->
                    Log.d(TAG, "##### onError #####")
                    for ((index, value) in throwable.stackTrace.withIndex()) {
                        Log.d(TAG, "$value")
                    }
                }, {
                    Log.d(TAG, "##### onComplete #####")
                }).apply {
                    view.disposables.add(this)
                }
        }
    }
}