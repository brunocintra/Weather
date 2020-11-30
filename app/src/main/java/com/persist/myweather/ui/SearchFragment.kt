package com.persist.myweather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.persist.myweather.R
import com.persist.myweather.adapter.SearchAdapter
import com.persist.myweather.database.WeatherDatabase
import com.persist.myweather.manager.OpenWeatherManager
import com.persist.myweather.model.City
import com.persist.myweather.model.CityDatabase
import com.persist.myweather.model.Element
import com.persist.myweather.model.Root

import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_search.setOnClickListener(this)
        recyclerView.adapter = SearchAdapter(mutableListOf())

        floatingActionButton.setOnClickListener {
            val city = edit_search.text.toString()
            val service = OpenWeatherManager().getOpenWeatherService()
            val call = service.getCityWeather(city)
            
            call.enqueue(object : Callback<City> {
                override fun onResponse(call: Call<City>, response: Response<City>) {
                    when (response.isSuccessful) {
                        true -> {
                            val city = response.body()

                            if (context != null){
                                val db = WeatherDatabase.getInstance(context!!)

                                val cityDatabase = CityDatabase(city!!.id, city!!.name)
                                val result = db?.cityDatabaseDao()?.save(cityDatabase)

                                Toast.makeText(context, getString(R.string.toast_save_favorite), Toast.LENGTH_SHORT).show()
                            }


                        }
                        false -> {

                            Log.e("BCC", "Response is not success")
                        }
                    }
                }

                override fun onFailure(call: Call<City>, t: Throwable) {
                    Log.e("BCC", "There is an error: ${t.message}")
                }
            })
        }
    }

    override fun onClick(view: View?) {
        when (view?.context?.let { isConnectivityAvaliable(it) }) {
            true -> {
                progressBar.visibility = View.VISIBLE

                val city = edit_search.text.toString()
                val serviceWeather = OpenWeatherManager().getOpenWeatherService()
                val call = serviceWeather.findTemperature(city)

                call.enqueue(object : Callback<Root> {
                    override fun onResponse(call: Call<Root>, response: Response<Root>) {
                        when (response.isSuccessful) {
                            true -> {
                                progressBar.visibility = View.GONE
                                val root = response.body()
                                Log.d("BCC", "Returned elements: $root")

                                val elements = mutableListOf<Element>()
                                root?.list?.forEach{
                                    elements.add(it)
                                }

                                (recyclerView.adapter as SearchAdapter).addItems(elements)
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.addItemDecoration(SearchAdapter.MyItemDecoration(30))
                            }
                            false -> {
                                progressBar.visibility = View.GONE
                                Log.e("BCC", "Response is not success")
                            }
                        }
                    }

                    override fun onFailure(call: Call<Root>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        Log.e("BCC", "There is an error: ${t.message}")
                    }
                })
            }
            false -> {
                Toast.makeText(view?.context, getString(R.string.toast_offline), Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("WrongConstant")
    fun isConnectivityAvaliable(context: Context): Boolean {
        var result = false
        val connMannager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connMannager.getNetworkCapabilities(connMannager.activeNetwork)?.run {
                result = when {
                    hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)  -> true
                    else -> false
                }
            }
        } else {
            connMannager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
        return result
    }

}