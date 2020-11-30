package com.persist.myweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.persist.myweather.dao.CityDatabaseDao
import com.persist.myweather.model.CityDatabase

@Database(entities = arrayOf(CityDatabase::class), version = 1)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun cityDatabaseDao(): CityDatabaseDao

    companion object {
        var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase? {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        "weather.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }

}