package com.selincengiz.kotlincountries.service

import com.selincengiz.kotlincountries.model.Model
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //GET , POST

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Model>>


}