package com.persist.myweather.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.persist.myweather.R
import com.persist.myweather.model.Element
import kotlinx.android.synthetic.main.fragment_search_recyclerview_item.view.*

class SearchAdapter(val list: MutableList<Element>?): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_search_recyclerview_item, parent, false)    )
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
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

    fun addItems(newElements: MutableList<Element>?){
        list?.clear()
        newElements?.forEach { list?.add(it) }
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtSearchCityName: TextView = itemView.txt_search_city_name
        private val txtSearchNumber: TextView = itemView.txt_search_number
        private val imgCityWeather: ImageView = itemView.img_city_weather

        fun bindView(element: Element) = with(itemView) {
            txtSearchCityName.text = element.name
            txtSearchNumber.text = element.id.toString()

            Glide.with(context)
                .load("http://openweathermap.org/img/wn/${element.weather[0].icon}@4x.png")
                .placeholder(R.drawable.ic_weather_placeholder)
                .error(R.drawable.ic_weather_placeholder)
                .circleCrop()
                .into(imgCityWeather)
        }
    }

    class MyItemDecoration(private val height: Int): RecyclerView.ItemDecoration() {
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