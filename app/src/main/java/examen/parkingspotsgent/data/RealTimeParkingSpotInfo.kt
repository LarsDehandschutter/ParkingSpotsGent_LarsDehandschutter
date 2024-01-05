package examen.parkingspotsgent.data

/**
 * RealTimeParkingSpotInfo with a name, availableSpaces, lat, lon.
 *
 * @property name the name of the parkingSpot.
 * @property availableSpaces the parkingSpot live count of parkingSpaces.
 * @constructor creates a parkingSpot with a name, availableSpaces,
 * latitude and longitude.
 */
data class RealTimeParkingSpotInfo(
    val name: String,
    val availableSpaces: Int,
    val lat: String,
    val lon: String
)
