package examen.parkingspotsgent.model



import kotlinx.serialization.Serializable

/**
 * the coordinates of the parkingSpotLocation.
 * @property lat the latitude coordinate of the parkingSpot.
 * @property lon the longitude coordinate of the parkingSpot.
 */
@Serializable
data class GeoPoint2d(
    val lat: Double,
    val lon: Double
)