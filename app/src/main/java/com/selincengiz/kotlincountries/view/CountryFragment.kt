package com.selincengiz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.selincengiz.kotlincountries.R
import com.selincengiz.kotlincountries.databinding.FragmentCountryBinding
import com.selincengiz.kotlincountries.util.downloadFromUrl
import com.selincengiz.kotlincountries.util.placeHolderProgressBar
import com.selincengiz.kotlincountries.viewmodel.CountryViewModel
import com.selincengiz.kotlincountries.viewmodel.FeedViewModel


class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dataBinding: FragmentCountryBinding

    private var countryUuid=0
    private  lateinit var viewModel : CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
      /*  _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view*/
        return dataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this@CountryFragment)[CountryViewModel::class.java]

        arguments?.let {
            countryUuid= CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel.getDataFromRoom(countryUuid)
        observeLiveData()
    }

   private fun observeLiveData(){
       viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
           it?.let {
              /* binding.countryName.text =it.name
               binding.countryCapital.text =it.capital
               binding.countryCurrency.text =it.currency
               binding.countryLanguage.text =it.language
               binding.countryRegion.text =it.region
               binding.flag.downloadFromUrl(it.flag,
                   placeHolderProgressBar(binding.flag.context)
               )*/
               dataBinding.selectedCountry=it

           }
       })
   }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}