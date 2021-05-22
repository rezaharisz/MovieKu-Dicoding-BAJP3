package com.rezaharis.movieku.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezaharis.movieku.BuildConfig
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.databinding.ItemFavoritesBinding
import com.rezaharis.movieku.view.DetailTvShowActivity
import com.rezaharis.movieku.view.DetailTvShowActivity.Companion.TV_SH0WS

class FavoriteTvShowsAdapter: PagedListAdapter<TvShowsEntities, FavoriteTvShowsAdapter.TvShowsHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowsEntities>() {
            override fun areItemsTheSame(oldItem: TvShowsEntities, newItem: TvShowsEntities): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowsEntities, newItem: TvShowsEntities): Boolean {
                return oldItem == newItem
            }

        }
    }

    class TvShowsHolder(private val binding: ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowsEntities: TvShowsEntities){
            with(binding){
                Glide.with(itemView)
                    .load(BuildConfig.BASE_IMAGE + tvShowsEntities.poster)
                    .override(150,220)
                    .into(ivPoster)
                tvFavorites.text = tvShowsEntities.tvShowsName
                tvRate.text = tvShowsEntities.rate.toString()
                tvReleasedate.text = tvShowsEntities.releasedate

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(TV_SH0WS, tvShowsEntities.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsHolder {
        val itemFavoritesBinding = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsHolder(itemFavoritesBinding)
    }

    override fun onBindViewHolder(holder: TvShowsHolder, position: Int) {
        val tvShowsFavorites = getItem(position)
        if (tvShowsFavorites != null){
            holder.bind(tvShowsFavorites)
        }
    }
}