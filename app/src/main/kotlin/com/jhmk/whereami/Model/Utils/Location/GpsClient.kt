package com.example.iriver.constraintsample.Model.Utils.Location

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Model.RxEventObject
import com.jhmk.whereami.Model.Utils.Permission.PermissionCheck
import com.jhmk.whereami.Utils.Location.GeoPoint
import com.jhmk.whereami.Utils.Location.GeoTrans

class GpsClient {
    private val TAG = this.javaClass.simpleName
    private lateinit var mLocationManager : LocationManager

    fun getCurrentLocation(context: Context, permission: String) {
        Log.d(TAG, "##### getCurrentLocation #####")
        PermissionCheck().checkPermission(context, permission, object : PermissionCheck.onCheckedListener {
            override fun onChecked(result: Boolean) {
                mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (result) {
                    try {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100L, 1F, locationListener)
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100L, 1F, locationListener)
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                        mLocationManager.removeUpdates(locationListener)
                        RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_CURRENT_LOCATION, null))
                    }
                } else {
                    mLocationManager.removeUpdates(locationListener)
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_CURRENT_LOCATION, null))
                }
            }
        })
    }

    fun removeLocationListener(){
        Log.d(TAG, "##### removeLocationListener #####")
        mLocationManager.removeUpdates(locationListener)
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "##### locationListener #####")
            val geoPoint = GeoPoint(location.longitude, location.latitude)
            val grs80Point = GeoTrans.convert(GeoTrans.GEO, GeoTrans.GRS80, geoPoint)
            Log.d(TAG, "##### locationListener ##### ${grs80Point.x} ${grs80Point.y}")
            RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_CURRENT_LOCATION, grs80Point))
            removeLocationListener()
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }
    }
}