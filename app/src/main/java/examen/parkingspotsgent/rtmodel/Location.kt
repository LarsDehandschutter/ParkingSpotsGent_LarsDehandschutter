package examen.parkingspotsgent.rtmodel


import kotlinx.serialization.Serializable
/**
 * the Location of the RealTimeParkingSpot.
 * @property lat the latitude coordinate of the real time parkingSpot.
 * @property lon the longitude coordinate of the real time parkingSpot.
 */
@Serializable
data class Location(
    val lat: Double,
    val lon: Double
)