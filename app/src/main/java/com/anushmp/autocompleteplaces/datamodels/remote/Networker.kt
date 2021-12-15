package com.anushmp.autocompleteplaces.datamodels.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Networker {

    companion object{

        var base_url = "https://digi-api.airtel.in/compassLocation/rest/address/"

        fun getRetrofit():Retrofit{

            val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()

            return retrofit

        }

    }

}