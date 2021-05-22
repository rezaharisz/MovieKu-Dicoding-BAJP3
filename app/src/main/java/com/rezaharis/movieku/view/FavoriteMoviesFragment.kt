package com.rezaharis.movieku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezaharis.movieku.adapter.FavoriteMovieAdapter
import com.rezaharis.movieku.databinding.FragmentFavoriteMoviesBinding
import com.rezaharis.movieku.viewmodel.ViewModelFactory
import com.rezaharis.movieku.viewmodel.favorites.FavoritesMovieViewModel

class FavoriteMoviesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var favoritesMovieViewModel: FavoritesMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = context?.let { ViewModelFactory.getInstance(it) }

        favoritesMovieViewModel = factory?.let { ViewModelProvider(this, it) }!![FavoritesMovieViewModel::class.java]

        val favoriteMovieAdapter = FavoriteMovieAdapter()
        activity?.let {
            favoritesMovieViewModel.getFavoriteMovies().observe(it, { listMovie ->
                if (listMovie != null){
                    favoriteMovieAdapter.submitList(listMovie)
                    favoriteMovieAdapter.notifyDataSetChanged()

                    with(binding.rvFavoritesMovies){
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = favoriteMovieAdapter
                        favoriteMovieAdapter.notifyDataSetChanged()
                    }
                }
            })
        }
    }
}