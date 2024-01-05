package examen.parkingspotsgent.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * ParkingspotLocations with a list of parkingSpots.
 *
 * @property results the list of different parkingSpots.
 * @constructor creates ParkingSpotLocations with a list of parkingSpots.
 */
@Suppress("SpellCheckingInspection")
@Serializable
data class ParkingspotLocations(
    val results: List<Result>,
    @SerialName("total_count")
    val totalCount: Int
)