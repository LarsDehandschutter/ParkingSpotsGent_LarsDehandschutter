package examen.parkingspotsgent.rtmodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * RealTimeParkingSpot with a list of real time parkingSpots.
 *
 * @property results the list of different real time parkingSpots.
 * @constructor creates RealTimeParkingSpot with a list of real time parkingSpots.
 */
@Serializable
data class RealTimeParkingSpot(
    val results: List<ResultRT>,
    @SerialName("total_count")
    val totalCount: Int
)