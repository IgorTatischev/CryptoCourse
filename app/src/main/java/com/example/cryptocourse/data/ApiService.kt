package com.example.cryptocourse.data

import com.example.cryptocourse.model.coins.Coin
import com.example.cryptocourse.model.descriptions.Description
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false")
    suspend fun getCoinsUSD(): Response<Coin>

    @GET("markets?vs_currency=eur&order=market_cap_desc&per_page=30&page=1&sparkline=false")
    suspend fun getCoinsEUR(): Response<Coin>

    @GET("{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false")
    suspend fun getCoinDescription(@Path("id") id : String?) : Response<Description>
}