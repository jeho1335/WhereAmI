package com.jhmk.whereami.Module.Utils.Network

import com.jhmk.whereami.Model.StationList
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class ApiClient {
    private val TAG = this.javaClass.simpleName
    private var mRetrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("http://swopenAPI.seoul.go.kr/api/subway/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    interface ApiService {
        @GET("4f71704f746a656832344c66466a7a/json/nearBy/0/4/ {gpsX} / {gpsY} ")
        fun getNearbyStations(@Path("gpsX") locationX: String, @Path("gpsY") locationY: String): Flowable<StationList>

        @GET("576d7270776a656832394d49517976/json/stationInfo/0/4/{stName}")
        fun getPrevNextStations(@Path("stName") stName: String): Flowable<StationList>
    }

    fun getRetrofitClient() = mRetrofit
}