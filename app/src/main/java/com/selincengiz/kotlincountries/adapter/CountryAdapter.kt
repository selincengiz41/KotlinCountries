package com.selincengiz.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.kotlincountries.databinding.ItemCountryBinding
import com.selincengiz.kotlincountries.model.Model

class CountryAdapter(val countryList:ArrayList<Model>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(val binding : ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)


    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.name.text=countryList.get(position).name
        holder.binding.region.text=countryList.get(position).region


    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList( newCountryList:List<Model>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}