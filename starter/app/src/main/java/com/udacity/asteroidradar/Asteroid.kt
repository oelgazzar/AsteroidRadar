package com.udacity.asteroidradar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

val testAsteroidList = arrayOf(
        Asteroid(
            3727639,
            "(2015 RO36)",
            "2015-09-08",
            22.9,
            0.1563291544,
            15.8053596703,
            0.0540392535,
            false
        ),
        Asteroid(
            3727181,
            "(2015 RN83)",
            "2015-09-08",
            21.7,
            0.2716689341,
            12.0811420305,
            0.1684193589,
            false
        ),
        Asteroid(
            3730577,
            "(2015 TX237)",
            "2015-09-08",
            23.3,
            0.130028927,
            6.573400491,
            0.0795238758,
            false
        ),
        Asteroid(
            3731587,
            "(2015 UG)",
            "2015-09-08",
            22.81,
            0.1629446024,
            11.9557600601,
            0.1132399881,
            false
        ),
        Asteroid(
            3747356,
            "(2016 EK158)",
            "2015-09-08",
            20.51,
            0.4699373665,
            16.9572901605,
            0.2804752334,
            false
        ),
        Asteroid(
            3758838,
            "(2016 RT)",
            "2015-09-08",
            24.4,
            0.0783501764,
            19.0983945697,
            0.170705627,
            false
        ),
        Asteroid(
            2002100,
            "2100 Ra-Shalom (1978 RA)",
            "2022-08-31",
            16.23,
            3.3731835891,
            11.3477738768,
            0.1873286606,
            false
        ),
        Asteroid(
            2523609,
            "523609 (2005 PJ2)",
            "2022-08-31",
            19.4,
            0.7835017643,
            14.4643409683,
            0.3735705542,
            true
        ),
        Asteroid(
            2411165,
            "411165 (2010 DF1)",
            "2022-08-27",
            21.98,
            0.2388031102,
            19.4326499113,
            0.1036224439,
            true
        ),
        Asteroid(
            2423709,
            "423709 (2006 BQ6)",
            "2022-08-27",
            19.76,
            0.6638041738,
            22.7444097145,
            0.4022204769,
            true
        ),
        Asteroid(
            3310464,
            "(2006 AN)",
            "2022-08-27",
            24.36,
            0.079806815,
            6.104565326,
            0.3177355965,
            false
        ),
        Asteroid(
            2161989,
            "161989 Cacus (1978 CA)",
            "2022-09-01",
            17.33,
            2.0325441072,
            14.288038323,
            0.0575389846,
            true
        )
).toList()