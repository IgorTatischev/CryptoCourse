package com.example.cryptocourse.ui

import androidx.lifecycle.LiveData
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

    private  val _coinsList : MutableLiveData<Response<Coin>> = MutableLiveData()
    val coinsList : LiveData<Response<Coin>> = _coinsList

    private val _description : MutableLiveData<Response<Description>> = MutableLiveData()
    val description : LiveData<Response<Description>> = _description

    private val _exception : MutableLiveData<String> = MutableLiveData()
    val exception : LiveData<String> = _exception

    fun setCoinsListUSD(root: String?){
        viewModelScope.launch {
            try {
                if (repo.getListUSD().body()?.size!! > 0) {
                    _coinsList.value = repo.getListUSD()
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
                    _coinsList.value = repo.getListEUR()
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
                   _description.value = repo.getTextDescription(id)
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
        _exception.value = ex
    }
}