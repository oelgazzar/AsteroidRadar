package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.asAsteroid
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.parseAsteroidsFromJson
import com.udacity.asteroidradar.network.retrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AsteroidRepository(val database: AsteroidDatabase) {
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = parseAsteroidsFromJson(
                retrofitService.getAsteroids(LocalDate.now().toString())
            )
            database.asteroidDao.insert(*asteroids.asDatabaseModel())
        }
    }

    fun getAsteroids(filter: LiveData<AsteroidFilter>): LiveData<List<Asteroid>> {
        return Transformations.switchMap(
            filter
        ) {
            val today = LocalDate.now().toString()
            val nextWeek = LocalDate.now().plusWeeks(1L).toString()
            Transformations.map(
                when(filter.value) {
                    AsteroidFilter.TODAY -> database.asteroidDao.getTodayAsteroids(today)
                    AsteroidFilter.WEEK -> database.asteroidDao.getWeekAsteroids(today, nextWeek)
                    else -> database.asteroidDao.getAllAsteroids(today)
                }
            ) {
                it.asAsteroid()
            }
        }
    }

    suspend fun deleteOldAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteOldAsteroids(LocalDate.now().toString())
        }
    }


}

enum class AsteroidFilter {
    TODAY, WEEK, ALL
}