package com.example.cryptocourse.data

import com.example.cryptocourse.model.coins.Coin
import com.example.cryptocourse.model.descriptions.Description
import retrofit2.Response

class Repository {

    suspend fun getListUSD(): Response<Coin> {
        return RetrofitClient.instance.getCoinsUSD()
    }

    suspend fun getListEUR(): Response<Coin> {
        return RetrofitClient.instance.getCoinsEUR()
    }

    suspend fun  getTextDescription(id: String?): Response<Description>{
        return RetrofitClient.instance.getCoinDescription(id)
    }
}