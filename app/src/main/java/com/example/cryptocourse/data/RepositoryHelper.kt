package com.example.cryptocourse.data

import com.example.cryptocourse.model.Coin
import retrofit2.Response

class RepositoryHelper {

    suspend fun getListUSD(): Response<Coin> {
        return RetrofitInstance.api.getCoinsUSD()
    }

    suspend fun getListEUR(): Response<Coin> {
        return RetrofitInstance.api.getCoinsEUR()
    }
}