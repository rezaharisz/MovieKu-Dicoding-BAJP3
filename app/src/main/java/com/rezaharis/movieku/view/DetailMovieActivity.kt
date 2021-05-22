package com.rezaharis.movieku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rezaharis.movieku.BuildConfig
import com.rezaharis.movieku.R
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.databinding.ActivityDetailMovieBinding
import com.rezaharis.movieku.viewmodel.movie.MovieDetailViewModel
import com.rezaharis.movieku.viewmodel.ViewModelFactory

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    companion object{
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        val factory = ViewModelFactory.getInstance(this)
        movieDetailViewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        movieDetailViewModel.getMovieId(intent.getIntExtra(MOVIE, 0)).observe(this, {
            if (it != null){
                val isFavorite = it.setFavorite
                getMovie(it)
                showLoading(false)

                if (isFavorite){
                    binding.setFavorite.setImageResource(R.drawable.ic_set_favorite)
                } else{
                    binding.setFavorite.setImageResource(R.drawable.ic_no_favorite)
                }

                binding.setFavorite.setOnClickListener {
                    movieDetailViewModel.setFavoriteMovies()
                }
            }
        })
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
        } else{
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun getMovie(movieEntities: MovieEntities){
        Glide.with(this)
                .load(BuildConfig.BASE_IMAGE + movieEntities.poster)
                .override(250, 320)
                .into(binding.ivPoster)
        binding.tvMovie.text = movieEntities.movieName
        binding.movieRelease.text = movieEntities.releasedate
        binding.movieRate.text = movieEntities.rate.toString()
        binding.movieVotecount.text = movieEntities.votecount.toString()
        binding.tvDes.text = movieEntities.description
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}