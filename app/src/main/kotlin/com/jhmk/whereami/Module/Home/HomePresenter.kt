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
import com.jhmk.whereami.R
import com.jhmk.whereami.Utils.Location.GeoPoint
import com.tedpark.tedpermission.rx2.TedRx2Permission
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

    override fun requestCurrentLocation(context: Context, stLine: String) {
        Log.d(TAG, "##### requestCurrentLocation #####")
        TedRx2Permission
            .with(context)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .request()
            .subscribe({ permissionResult ->
                if (permissionResult.isGranted) {
                    getCurrentLocation(context, stLine)
                } else {
                    mView.onResultMessage(view.getString(R.string.string_request_gps_location_denied))
                }
            }, { throwable ->
                throwable.printStackTrace()
                mView.onResultMessage(view.getString(R.string.string_unknown_error))
            }).apply {
                view.disposables.add(this)
            }
    }

    override fun requestNearbyStation(context: Context, line: String?, geoPoint: GeoPoint) {
        Log.d(TAG, "##### requestNearbyStation #####")
        val lineList = CurrentLineManager(context).lineInfoList
        for ((index, value) in lineList.withIndex()) {
            if (value.name == line) {
                mCurrentLine = lineList[index].name
                break
            }
        }
        getNearByStation(geoPoint)
    }

    private fun getCurrentLocation(context: Context, stLine: String) {
        Log.d(TAG, "##### getCurrentLocation #####")
        GpsClient().getCurrentLocation(context, { currentLocation ->
            view.onResultCurrentLocation(currentLocation, stLine)
        }) { isSuccess ->
            if (!isSuccess) {
                mView.onResultMessage(view.getString(R.string.string_request_gps_failed))
            }
        }
    }

    private fun getNearByStation(currentLocation: GeoPoint) {
        Log.d(TAG, "##### getNearByStation #####")
        mView.onResultChangeProgressMessage(view.getString(R.string.string_request_nearby_station_progress_title))
        ApiClient().getRetrofitClient().create(ApiClient.ApiService::class.java).run {
            this.getNearbyStations(currentLocation.x.toString(), currentLocation.y.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { stations ->
                    stations.stationList?.get(0)?.statnNm ?: ""
                }
                .filter { stationName ->
                    (stationName != "").also {
                        if (it) {
                            mView.onResultNearbyStation(true, stationName, "")
                            mView.onResultChangeProgressMessage(view.getString(R.string.string_request_nearby_station_prev_next_progress_title))
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
                    mView.onResultMessage(view.getString(R.string.string_request_gps_success))
                }, { throwable ->
                    Log.d(TAG, "##### onError #####")
                    for ((index, value) in throwable.stackTrace.withIndex()) {
                        Log.d(TAG, "$value")
                    }
                    mView.onResultMessage(view.getString(R.string.string_unknown_error))
                }, {
                    Log.d(TAG, "##### onComplete #####")
                }).apply {
                    view.disposables.add(this)
                }
        }
    }
}