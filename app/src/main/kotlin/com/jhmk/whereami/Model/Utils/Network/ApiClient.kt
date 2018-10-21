package com.jhmk.whereami.Model.Utils.Network

import android.util.Log
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Model.RxEvent
import com.jhmk.whereami.Model.RxEventObject
import com.jhmk.whereami.Model.UserStations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class ApiClient {
    private val TAG = this.javaClass.simpleName
    private var mRetrofit : Retrofit = Retrofit
            .Builder()
            .baseUrl("http://swopenAPI.seoul.go.kr/api/subway/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    interface ApiService{
        @GET("4f71704f746a656832344c66466a7a/json/nearBy/0/4/ {gpsX} / {gpsY} ")
        fun getNearbyStations(@Path("gpsX") locationX : String, @Path("gpsY") locationY : String): Call<UserStations>
    }

    fun getNearbyStation(locationX : String, locationY : String){
        val service = mRetrofit.create(ApiService::class.java)
        val request = service.getNearbyStations(locationX, locationY)
        request.enqueue(object : Callback<UserStations> {
            override fun onResponse(call: Call<UserStations>, response: Response<UserStations>) {
                val userStations = response.body()
                if(userStations == null){
                    Log.d(TAG, "##### onResponse ##### ${response.body()}")
                    RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, null))
                }else {
                  RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, userStations))
                }
            }

            override fun onFailure(call: Call<UserStations>, t: Throwable) {
                Log.d(TAG, "##### onFailure ##### ${t.localizedMessage}")
                RxEvent.sendEvent(RxEventObject(ConstVariables.RX_RESPONSE_NEARBY_STATIONS, null))
            }
        })
    }
}