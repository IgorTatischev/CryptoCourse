package com.example.cryptocourse.coinListFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocourse.data.RepositoryHelper
import com.example.cryptocourse.model.Coin
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinListViewModel : ViewModel() {

    var repo = RepositoryHelper()
    val coinsList : MutableLiveData<Response<Coin>> = MutableLiveData()

    fun getCoinsListUSD(){
        viewModelScope.launch {
            coinsList.value = repo.getListUSD()
        }
    }
}