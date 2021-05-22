package com.rezaharis.movieku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezaharis.movieku.adapter.FavoriteTvShowsAdapter
import com.rezaharis.movieku.databinding.FragmentFavoriteTvShowsBinding
import com.rezaharis.movieku.viewmodel.ViewModelFactory
import com.rezaharis.movieku.viewmodel.favorites.FavoritesTvShowsViewModel

class FavoriteTvShowsFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTvShowsBinding
    private lateinit var favoritesTvShowsViewModel: FavoritesTvShowsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = context?.let { ViewModelFactory.getInstance(it) }

        val favoriteTvShowsAdapter = FavoriteTvShowsAdapter()
        favoritesTvShowsViewModel = factory?.let { ViewModelProvider(this, it) }!![FavoritesTvShowsViewModel::class.java]
        activity?.let {
            favoritesTvShowsViewModel.getFavoriteTvShows().observe(it, {listTvShows ->
                if (listTvShows != null){
                    favoriteTvShowsAdapter.submitList(listTvShows)
                    favoriteTvShowsAdapter.notifyDataSetChanged()

                    with(binding.rvFavoritesTvshows){
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = favoriteTvShowsAdapter
                        favoriteTvShowsAdapter.notifyDataSetChanged()
                    }
                }
            })
        }
    }
}