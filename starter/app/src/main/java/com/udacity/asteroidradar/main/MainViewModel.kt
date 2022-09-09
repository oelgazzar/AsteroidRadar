package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.retrofitService
import com.udacity.asteroidradar.repository.AsteroidFilter
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application.applicationContext)
    private val repository = AsteroidRepository(database)

    private val filter = MutableLiveData<AsteroidFilter>(AsteroidFilter.ALL)
    val asteroids = repository.getAsteroids(filter)

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetails: LiveData<Asteroid?> get() = _navigateToAsteroidDetails

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val isLoading: LiveData<Boolean> get() = Transformations.map(_networkStatus) {it == NetworkStatus.LOADING}

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> get() = _pictureOfDay

    private val _showSnackBarMessage = MutableLiveData<String?>()
    val showSnackbarMessage: LiveData<String?> get() = _showSnackBarMessage


    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                repository.refreshAsteroids()
                _pictureOfDay.value = retrofitService.getPictureOfDay()
            } catch (e: Throwable){
                _showSnackBarMessage.value = "Failure: Unable to get data from network"
            }
        }
    }

    fun onNavigateToAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onNavigationToAsteroidDetailsDone() {
        _navigateToAsteroidDetails.value = null
    }

    fun updateAsteroidFilter(filter: AsteroidFilter) {
        this.filter.value = filter
    }

    fun showSnackBarMessageDone() {
        _showSnackBarMessage.value = null
    }
}

enum class NetworkStatus {
    LOADING, FAILED, DONE
}