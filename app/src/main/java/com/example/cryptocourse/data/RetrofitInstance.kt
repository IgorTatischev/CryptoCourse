package com.example.cryptocourse.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        Retrofit.Builder().baseUrl("https://api.coingecko.com/api/v3/coins/")
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //to endpoints
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}