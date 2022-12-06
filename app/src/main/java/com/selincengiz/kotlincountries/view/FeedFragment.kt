package com.selincengiz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.selincengiz.kotlincountries.R
import com.selincengiz.kotlincountries.adapter.CountryAdapter
import com.selincengiz.kotlincountries.databinding.FragmentFeedBinding
import com.selincengiz.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this@FeedFragment)[FeedViewModel::class.java]
        viewModel.refreshData()
        binding.recyclerView.layoutManager= LinearLayoutManager(context)
        binding.recyclerView.adapter=countryAdapter

        binding.SwipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility=View.GONE
            binding.countryError.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            binding.SwipeRefreshLayout.isRefreshing=false
            viewModel.refreshFromAPI()
        }
        observeLiveData()



    }
   private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerView.visibility=View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.countryError.visibility=View.VISIBLE

                }
                else{
                    binding.countryError.visibility=View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.countryLoading.visibility=View.VISIBLE
                    binding.recyclerView.visibility=View.GONE
                    binding.countryError.visibility=View.GONE
                }
                else{
                    binding.countryLoading.visibility=View.GONE

                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}