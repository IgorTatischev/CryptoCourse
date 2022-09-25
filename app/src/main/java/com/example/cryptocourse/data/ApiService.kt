package com.example.cryptocourse.data

import com.example.cryptocourse.model.Coin
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false")
    suspend fun getCoinsUSD(): Response<Coin>

    @GET("markets?vs_currency=eur&order=market_cap_desc&per_page=30&page=1&sparkline=false")
    suspend fun getCoinsEUR(): Response<Coin>


}