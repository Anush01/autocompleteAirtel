package com.anushmp.autocompleteplaces.datamodels.remote

import com.anushmp.autocompleteplaces.datamodels.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApi {

    @GET("autocomplete")
    fun getResults(@Query("queryString") queryString: String): Call<Json4Kotlin_Base>
}