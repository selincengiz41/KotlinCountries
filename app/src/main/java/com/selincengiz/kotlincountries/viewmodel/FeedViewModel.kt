package com.selincengiz.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selincengiz.kotlincountries.model.Model
import com.selincengiz.kotlincountries.service.CountryDatabase
import com.selincengiz.kotlincountries.service.CountryService
import com.selincengiz.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application)  {
    private val countryService =CountryService()
    //Kullan at büyük veriler için oluşturulur sonra silinir
    private val disposable =CompositeDisposable()
    private  var customPreferences =CustomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L

    val countries = MutableLiveData<List<Model>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime =customPreferences.getTime()
        if(updateTime!=null && updateTime !=0L && System.nanoTime()-updateTime< refreshTime ){
          getDataFromSQLite()
        }
        else{
            getDataFromAPI()
        }


    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            showCountries(dao.getAllCountries())
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()

        }
    }
    private fun getDataFromAPI(){
        countryLoading.value=true
        disposable.add(
            countryService.getData()
                .subscribeOn(Schedulers.newThread()) //asenkrone olarak yapılacak
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Model>>(){
                    override fun onSuccess(t: List<Model>) {
                    storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        countryError.value=true
                        countryLoading.value=false
                        e.printStackTrace()

                    }

                })
        )

    }

    private fun showCountries(countryList :List<Model>){
    countries.value=countryList
    countryError.value=false
    countryLoading.value=false
}
    private fun storeInSQLite(countryList :List<Model>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong =dao.insertAll(*countryList.toTypedArray()) // list => individual
            var i=0
            while (i<countryList.size){
                countryList[i].uuid=listLong[i].toInt()
                i=i+1
            }
            showCountries(countryList)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}