package com.persist.myweather.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.persist.myweather.R
import com.persist.myweather.model.CityDatabase
import kotlinx.android.synthetic.main.fragment_favorite_recyclerview_item.view.*
import kotlinx.android.synthetic.main.fragment_search_recyclerview_item.view.*

class FavoriteAdapter(val list: List<CityDatabase>?): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        return FavoriteAdapter.FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_favorite_recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        when (holder) {
            is FavoriteAdapter.FavoriteViewHolder -> {
                if (position < (list?.size ?: 0)) {
                    val element = list?.get(position)
                    if (element != null) {
                        holder.bindView(element)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtFavoriteCityName: TextView = itemView.txt_favorite_city_name
        private val txtFavoriteCityId: TextView = itemView.txt_favorite_city_id

        fun bindView(cityDatabase: CityDatabase) = with(itemView) {
            txtFavoriteCityName.text = cityDatabase.cityName
            txtFavoriteCityId.text = cityDatabase.id.toString()
        }
    }

    class FavoriteItemDecoration(private val height: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                if(parent.getChildAdapterPosition(view) == 0) {
                    top = height
                }

                left = height
                right = height
                bottom = height
            }

        }
    }

}