package com.example.cryptocourse.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocourse.data.RepositoryHelper
import com.example.cryptocourse.model.coins.Coin
import com.example.cryptocourse.model.descriptions.Description
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinViewModel : ViewModel() {

    var repo = RepositoryHelper()
    val coinsList : MutableLiveData<Response<Coin>> = MutableLiveData()
    val description : MutableLiveData<Response<Description>> = MutableLiveData()

    fun getCoinsListUSD(){
        viewModelScope.launch {
            coinsList.value = repo.getListUSD()
        }
    }

    fun getCoinsListEUR(){
        viewModelScope.launch {
            coinsList.value = repo.getListEUR()
        }
    }

   fun  getDescription(id: String?){
       viewModelScope.launch {
           description.value = repo.getTextDescription(id)
       }
   }
}