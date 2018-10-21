package com.jhmk.whereami.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserStations {

    @SerializedName("stationList")
    @Expose
    var stationList: List<Stations>? = null

    /*@SerializedName("errorMessage")
    @Expose
    var errorMessage: ErrorMessage? = null*/
}