package com.selincengiz.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selincengiz.kotlincountries.model.Model
import com.selincengiz.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application ) {
    val countryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom(countryId :Int){
    launch {
        val dao = CountryDatabase(getApplication()).countryDao()

        countryLiveData.value= dao.getCountry(countryId)
    }


    }
}