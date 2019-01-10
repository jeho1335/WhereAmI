package com.example.iriver.constraintsample.Model.Utils.Location

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.jhmk.whereami.Utils.Location.GeoPoint
import com.jhmk.whereami.Utils.Location.GeoTrans

class GpsClient {
    private val TAG = this.javaClass.simpleName
    private lateinit var mLocationManager: LocationManager

    fun getCurrentLocation(context : Context?, currentLocation : (GeoPoint) -> Unit) {
        Log.d(TAG, "##### getCurrentLocation #####")
        if(context == null){
            return
        }
        try {
            mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100L, 1F, object : LocationListener{
                override fun onLocationChanged(location: Location) {
                    Log.d(TAG, "##### locationListener #####")
                    val geoPoint = GeoPoint(location.longitude, location.latitude)
                    val grs80Point = GeoTrans.convert(GeoTrans.GEO, GeoTrans.GRS80, geoPoint)
                    Log.d(TAG, "##### locationListener ##### ${grs80Point.x} ${grs80Point.y}")
                    currentLocation(grs80Point)
                    mLocationManager.removeUpdates(this)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }
            })
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}