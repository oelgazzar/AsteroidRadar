package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query(value="start_date") startDate: String,
        @Query(value="api_key") API_KEY:String = BuildConfig.NASA_API_KEY
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query(value="api_key") API_KEY:String = BuildConfig.NASA_API_KEY
    ): PictureOfDay
}

val retrofitService: AsteroidApi by lazy {
    retrofit.create(AsteroidApi::class.java)
}