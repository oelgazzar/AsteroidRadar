package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.util.TEST_JSON_STRING
import org.json.JSONObject

data class NetworkAsteroid(val id: Long, val codename: String, val closeApproachDate: String,
                      val absoluteMagnitude: Double, val estimatedDiameter: Double,
                      val relativeVelocity: Double, val distanceFromEarth: Double,
                      val isPotentiallyHazardous: Boolean)

fun List<NetworkAsteroid>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        AsteroidEntity(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun parseAsteroidsFromJson(jsonString: String = TEST_JSON_STRING): List<NetworkAsteroid> {
    val asteroidList = mutableListOf<NetworkAsteroid>()

    val json = JSONObject(jsonString).getJSONObject("near_earth_objects")

    for (date in json.keys()) {
        val asteroidJsonArray = json.getJSONArray(date)
        repeat(asteroidJsonArray.length()){ index ->
            val asteroidJson = asteroidJsonArray.getJSONObject(index)

            val id = asteroidJson.getString("id").toLong()
            val name = asteroidJson.getString("name")
            val closeApproachData = asteroidJson.getJSONArray("close_approach_data")
                .getJSONObject(0)
            val magnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val diameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")
            val velocity = closeApproachData.getJSONObject("relative_velocity")
                .getString("kilometers_per_second").toDouble()
            val distance = closeApproachData.getJSONObject("miss_distance")
                .getString("astronomical").toDouble()
            val status = asteroidJson.getBoolean("is_potentially_hazardous_asteroid")

            asteroidList.add(
                NetworkAsteroid(
                id,
                name,
                date,
                magnitude,
                diameter,
                velocity,
                distance,
                status
            ))
        }
    }

    return asteroidList
}