package com.rezaharis.movieku.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rezaharis.movieku.view.FavoriteMoviesFragment
import com.rezaharis.movieku.view.FavoriteTvShowsFragment

class SectionPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position){
            0 -> fragment = FavoriteMoviesFragment()
            1 -> fragment = FavoriteTvShowsFragment()
        }

        return fragment as Fragment
    }

}