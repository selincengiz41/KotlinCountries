package com.selincengiz.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.kotlincountries.R
import com.selincengiz.kotlincountries.databinding.ItemCountryBinding
import com.selincengiz.kotlincountries.model.Model
import com.selincengiz.kotlincountries.util.downloadFromUrl
import com.selincengiz.kotlincountries.util.placeHolderProgressBar
import com.selincengiz.kotlincountries.view.FeedFragmentDirections

class CountryAdapter(val countryList:ArrayList<Model>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {
    class CountryViewHolder(val binding : ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.from(parent.context), R.layout.item_country,parent, false)
        return CountryViewHolder(binding)


    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country=countryList.get(position)
//kullanılmıyor data binding fonksiyon örneği
        holder.binding.listener=this
      holder.binding.root.setOnClickListener {
           val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList.get(position).uuid)
           Navigation.findNavController(it).navigate(action)
       }
        /* holder.binding.imageView.downloadFromUrl(countryList.get(position).flag,
            placeHolderProgressBar(holder.binding.imageView.context))
 */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList( newCountryList:List<Model>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


    override fun onCountryClicked(v: View) {
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
        Navigation.findNavController(v).navigate(action)
    }
}