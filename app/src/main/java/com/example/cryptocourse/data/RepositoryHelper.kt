package com.example.cryptocourse.data

import com.example.cryptocourse.model.coins.Coin
import com.example.cryptocourse.model.descriptions.Description
import retrofit2.Response

class RepositoryHelper {

    suspend fun getListUSD(): Response<Coin> {
        return RetrofitInstance.api.getCoinsUSD()
    }

    suspend fun getListEUR(): Response<Coin> {
        return RetrofitInstance.api.getCoinsEUR()
    }

    suspend fun  getTextDescription(id: String?): Response<Description>{
        return RetrofitInstance.api.getCoinDescription(id)
    }
}