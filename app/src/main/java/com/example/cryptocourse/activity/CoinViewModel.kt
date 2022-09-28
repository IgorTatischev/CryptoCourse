package com.example.cryptocourse.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocourse.data.Repository
import com.example.cryptocourse.model.coins.Coin
import com.example.cryptocourse.model.descriptions.Description
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinViewModel : ViewModel() {

    var repo = Repository()
    val coinsList : MutableLiveData<Response<Coin>> = MutableLiveData()
    val description : MutableLiveData<Response<Description>> = MutableLiveData()
    val exception : MutableLiveData<String> = MutableLiveData()

    fun setCoinsListUSD(root: String?){
        viewModelScope.launch {
            try {
                if (repo.getListUSD().body()?.size!! > 0) {
                    coinsList.value = repo.getListUSD()
                }
                else {
                    if (root == "chips") setException("create")
                    else if (root == "swipe") setException("refresh")
                }
            }
            catch (ex : Exception){
                if (root == "chips") setException("create")
                else if (root == "swipe") setException("refresh")
            }
        }
    }

    fun setCoinsListEUR(root: String?){
        viewModelScope.launch {
            try {
                if (repo.getListEUR().body()?.size!! > 0) {
                    coinsList.value = repo.getListEUR()
                }
                else {
                    if (root == "chips") setException("create")
                    else if (root == "swipe") setException("refresh")
                }
            }
            catch (ex : Exception){
                if (root == "chips") setException("create")
                else if (root == "swipe") setException("refresh")
            }
        }
    }

   fun  setDescription(id: String?){
       viewModelScope.launch {
           try {
               if (repo.getTextDescription(id).body()?.name != null) {
                   description.value = repo.getTextDescription(id)
               }
               else {
                   setException("exception")
               }
           }
           catch (ex : Exception){
               setException("exception")
           }
       }
   }

    private fun setException(ex : String){
        exception.value = ex
    }
}