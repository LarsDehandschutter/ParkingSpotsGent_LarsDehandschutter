package examen.parkingspotsgent.model



import kotlinx.serialization.Serializable

@Serializable
data class GeoPoint2d(
    val lat: Double,
    val lon: Double
)