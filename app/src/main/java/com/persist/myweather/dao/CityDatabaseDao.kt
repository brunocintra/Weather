package com.persist.myweather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.persist.myweather.model.CityDatabase

@Dao
interface CityDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cityDatabase: CityDatabase)

    @Query("SELECT * FROM citydatabase")
    fun getAllCityDatabase(): List<CityDatabase>
}