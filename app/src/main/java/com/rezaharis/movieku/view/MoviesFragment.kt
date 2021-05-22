package com.rezaharis.movieku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezaharis.movieku.adapter.MovieAdapter
import com.rezaharis.movieku.databinding.FragmentMoviesBinding
import com.rezaharis.movieku.viewmodel.ViewModelFactory
import com.rezaharis.movieku.viewmodel.movie.MovieViewModel
import com.rezaharis.movieku.vo.Status

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val factory = context?.let { ViewModelFactory.getInstance(it) }
            movieViewModel = factory?.let { ViewModelProvider(this, it) }!![MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()
            activity?.let {
                movieViewModel.getMovies().observe(it, { listMovie ->
                    if (listMovie != null){
                        when(listMovie.status){
                            Status.LOADING -> showLoading(true)
                            Status.SUCCESS -> {
                                showLoading(false)

                                movieAdapter.submitList(listMovie.data)
                                movieAdapter.notifyDataSetChanged()

                                with(binding.crRview){
                                    adapter = movieAdapter
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