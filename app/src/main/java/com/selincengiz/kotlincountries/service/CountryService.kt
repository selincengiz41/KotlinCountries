package com.selincengiz.kotlincountries.service

import com.selincengiz.kotlincountries.model.Model
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryService {

    private val BASE_URL ="https://raw.githubusercontent.com/"
    private val api =Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getData() :Single<List<Model>>{
        return api.getCountries()


    }

}