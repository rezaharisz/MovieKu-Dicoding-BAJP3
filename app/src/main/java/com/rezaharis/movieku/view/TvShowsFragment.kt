package com.rezaharis.movieku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezaharis.movieku.adapter.TvShowsAdapter
import com.rezaharis.movieku.databinding.FragmentTvshowsBinding
import com.rezaharis.movieku.viewmodel.ViewModelFactory
import com.rezaharis.movieku.viewmodel.tvshows.TvShowsViewModel
import com.rezaharis.movieku.vo.Status

class TvShowsFragment : Fragment() {

    private lateinit var binding: FragmentTvshowsBinding
    private lateinit var tvShowsViewModel: TvShowsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvshowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val factory = context?.let { ViewModelFactory.getInstance(it) }
            tvShowsViewModel = factory?.let { ViewModelProvider(this, it) }!![TvShowsViewModel::class.java]

            val tvShowsAdapter = TvShowsAdapter()
            activity?.let {
                tvShowsViewModel.getTvShows().observe(it, { listTvShows ->
                    if (listTvShows != null){
                        when(listTvShows.status){
                            Status.LOADING -> showLoading(true)
                            Status.SUCCESS -> {
                                showLoading(false)
                                tvShowsAdapter.submitList(listTvShows.data)
                                tvShowsAdapter.notifyDataSetChanged()

                                with(binding.crTvshows){
                                    this.adapter = tvShowsAdapter
                                    this.layoutManager = LinearLayoutManager(context)
                                }
                            }
                            Status.ERROR -> {
                                showLoading(false)
                                Toast.makeText(context, "Kesalahan Ketika Memuat Data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
        } else{
            binding.progressbar.visibility = View.GONE
        }
    }

}