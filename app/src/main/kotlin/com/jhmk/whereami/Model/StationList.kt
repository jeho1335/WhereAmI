package com.jhmk.whereami.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StationList {

    @SerializedName("stationList")
    @Expose
    var stationList: List<StationInfo>? = null

    /*@SerializedName("errorMessage")
    @Expose
    var errorMessage: ErrorMessage? = null*/
}