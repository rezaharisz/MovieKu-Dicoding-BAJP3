package com.rezaharis.movieku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rezaharis.movieku.BuildConfig
import com.rezaharis.movieku.R
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.databinding.ActivityDetailTvShowBinding
import com.rezaharis.movieku.viewmodel.tvshows.TvShowsDetailViewModel
import com.rezaharis.movieku.viewmodel.ViewModelFactory

class DetailTvShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTvShowBinding
    private lateinit var tvShowsDetailViewModel: TvShowsDetailViewModel

    companion object{
        const val TV_SH0WS = "tv_shows"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        val factory = ViewModelFactory.getInstance(this)
        tvShowsDetailViewModel = ViewModelProvider(this, factory)[TvShowsDetailViewModel::class.java]

        tvShowsDetailViewModel.getTvShowsId(intent.getIntExtra(TV_SH0WS, 0)).observe(this, {
            if (it != null){
                val isFavorite = it.setFavorite
                getTvShows(it)
                showLoading(false)

                if (isFavorite){
                    binding.setFavorite.setImageResource(R.drawable.ic_set_favorite)
                } else{
                    binding.setFavorite.setImageResource(R.drawable.ic_no_favorite)
                }

                binding.setFavorite.setOnClickListener {
                    tvShowsDetailViewModel.setFavoriteTvShows()
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

    private fun getTvShows(tvShowsEntities: TvShowsEntities){
        Glide.with(this)
                .load(BuildConfig.BASE_IMAGE + tvShowsEntities.poster)
                .override(250, 320)
                .into(binding.ivPosterTvshows)
        binding.tvshowsRelease.text = tvShowsEntities.releasedate
        binding.tvshowsRate.text = tvShowsEntities.rate.toString()
        binding.tvshowsVotecount.text = tvShowsEntities.votecount.toString()
        binding.tvDesTvshows.text = tvShowsEntities.description
        binding.tvTvshows.text = tvShowsEntities.tvShowsName
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}