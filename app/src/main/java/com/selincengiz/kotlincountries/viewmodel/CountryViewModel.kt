package com.selincengiz.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selincengiz.kotlincountries.model.Model

class CountryViewModel:ViewModel() {
    val countryName = MutableLiveData<List<Model>>()
    val countryLanguage = MutableLiveData<Boolean>()
    val countryCurrency = MutableLiveData<Boolean>()
    val countryRegion = MutableLiveData<Boolean>()
    val countryCapital = MutableLiveData<Boolean>()
}