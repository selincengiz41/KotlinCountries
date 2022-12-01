package com.selincengiz.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selincengiz.kotlincountries.model.Model

class FeedViewModel: ViewModel()  {
    val countries = MutableLiveData<List<Model>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val country =Model("Turkey","Ankara","Asia","TRY","www.ss.com","Turkish")
        val country2 =Model("France","Paris","Europe","EUR","www.ss.com","French")
        val country3 =Model("Germany","Berlin","Europe","EUR","www.ss.com","German")

        val countryList = arrayListOf<Model>(country,country2,country3)

        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
}