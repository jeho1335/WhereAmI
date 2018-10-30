package com.jhmk.whereami.Module.Utils.Network

import android.util.Log
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Model.RxEventObject
import com.jhmk.whereami.Model.StationList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class ApiClient {
    private val TAG = this.javaClass.simpleName
    private var mRetrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("http://swopenAPI.seoul.go.kr/api/subway/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    interface ApiService {
        @GET("4f71704f746a656832344c66466a7a/json/nearBy/0/4/ {gpsX} / {gpsY} ")
        fun getNearbyStations(@Path("gpsX") locationX: String, @Path("gpsY") locationY: String): Call<StationList>

        @GET("576d7270776a656832394d49517976/json/stationInfo/0/4/{stName}")
        fun getPrevNextStations(@Path("stName") stName: String): Call<StationList>
    }

    fun getNearbyStation(locationX: String, locationY: String) {
        Log.d(TAG, "##### getNearByStation ##### $locationX $locationY")
        val service = mRetrofit.create(ApiService::class.java)
        val request = service.getNearbyStations(locationX, locationY)
        request.enqueue(object : Callback<StationList> {
            override fun onResponse(call: Call<StationList>, response: Response<StationList>) {
                Log.d(TAG, "##### getNearbyStation onResponse #####")
                val userStations = response.body()
                if (userStations == null) {
                    Log.d(TAG, "##### getNearbyStation onResponse ##### ${response.body()}")
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, null))
                } else {
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, userStations))
                }
            }

            override fun onFailure(call: Call<StationList>, t: Throwable) {
                Log.d(TAG, "##### onFailure ##### ${t.localizedMessage}")
                RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, null))
            }
        })
    }

    fun getPrevNextStations(stName : String?){
        Log.d(TAG, "##### getPrevNextStations ##### $stName")
        if(stName == null){
            RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_PREVNEXT_STATIONS, null))
            return
        }
        val service = mRetrofit.create(ApiService::class.java)
        val request = service.getPrevNextStations(stName)
        request.enqueue(object : Callback<StationList>{
            override fun onResponse(call: Call<StationList>, response: Response<StationList>) {
                Log.d(TAG, "##### getPrevNextStations onResponse ${response.body()?.stationList} #####")
                val userStations = response.body()
                if (userStations == null) {
                    Log.d(TAG, "##### getPrevNextStations onResponse ##### ${response.body()}")
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_PREVNEXT_STATIONS, null))
                } else {
                    Log.d(TAG, "##### getPrevNextStations onResponse ##### ${userStations.stationList?.size}")
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_PREVNEXT_STATIONS, userStations))
                }
            }

            override fun onFailure(call: Call<StationList>, t: Throwable) {
                Log.d(TAG, "##### onFailure ##### ${t.localizedMessage}")
                RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_PREVNEXT_STATIONS, null))
            }

        })

    }
}