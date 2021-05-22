package com.rezaharis.movieku.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezaharis.movieku.BuildConfig
import com.rezaharis.movieku.view.DetailMovieActivity
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.databinding.ItemFavoritesBinding
import com.rezaharis.movieku.view.DetailMovieActivity.Companion.MOVIE

class MovieAdapter: PagedListAdapter<MovieEntities, MovieAdapter.MovieHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntities>(){
            override fun areItemsTheSame(oldItem: MovieEntities, newItem: MovieEntities): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntities, newItem: MovieEntities): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MovieHolder(private val binding: ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntities: MovieEntities){
            with(binding){
                Glide.with(itemView)
                    .load(BuildConfig.BASE_IMAGE + movieEntities.poster)
                    .override(150,220)
                    .into(ivPoster)
                tvFavorites.text = movieEntities.movieName
                tvRate.text = movieEntities.rate.toString()
                tvReleasedate.text = movieEntities.releasedate

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(MOVIE, movieEntities.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemMovie = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(itemMovie)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null){
            holder.bind(movies)
        }
    }
}