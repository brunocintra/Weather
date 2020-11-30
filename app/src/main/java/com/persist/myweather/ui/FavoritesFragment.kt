package com.persist.myweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.persist.myweather.R
import com.persist.myweather.adapter.FavoriteAdapter
import com.persist.myweather.database.WeatherDatabase
import kotlinx.android.synthetic.main.fragment_favorites.*

import kotlinx.android.synthetic.main.fragment_search.*

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db =  context?.let { WeatherDatabase.getInstance(it) }
        val list = db?.cityDatabaseDao()?.getAllCityDatabase()

        favoriteRecyclerView.adapter = FavoriteAdapter(list)
        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        favoriteRecyclerView.addItemDecoration(FavoriteAdapter.FavoriteItemDecoration(20))
    }
}