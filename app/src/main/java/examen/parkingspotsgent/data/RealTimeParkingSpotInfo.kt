package examen.parkingspotsgent.data

// Real time parking info that is useful for the application
data class RealTimeParkingSpotInfo(
    val name: String,
    val availableSpaces: Int,
    val lat: String,
    val lon: String
)
