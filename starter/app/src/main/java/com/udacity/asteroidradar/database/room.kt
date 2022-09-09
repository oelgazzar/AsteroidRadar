package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= :today ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(today: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= :today AND closeApproachDate < :nextWeek ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(today: String, nextWeek: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate == :today ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(today: String): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroids: AsteroidEntity)

    @Query("Delete from asteroids WHERE closeApproachDate < :today")
    fun deleteOldAsteroids(today: String)
}

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            return synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AsteroidDatabase::class.java,
                        "asteroid database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                instance
            }
        }
    }
}