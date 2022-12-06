package com.selincengiz.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.selincengiz.kotlincountries.model.Model

@Dao
interface CountryDao {
    //Data access object

    @Insert
    suspend fun  insertAll(vararg  countries :Model) :List<Long>

    // suspend -> pause & resume
    // vararg -> multipile country object
    // List<Long> -> primary keys döndürür
    @Query("SELECT * FROM model")
    suspend fun getAllCountries() :List<Model>

    @Query("SELECT * FROM model WHERE uuid= :countryId")
    suspend fun getCountry(countryId :Int):Model

    @Query("DELETE FROM model")
    suspend fun deleteAllCountries()

}