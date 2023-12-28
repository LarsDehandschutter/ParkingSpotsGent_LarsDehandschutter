package examen.parkingspotsgent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parkingSpots")
data class ParkingSpotInfo(
    @PrimaryKey
    val id: String,
    val capacity: Int,
    val houseNr: String,
    val infoText: String,
    val name: String,
    val streetName: String,
    val type: String,
    val lon: Double,
    val lat: Double
    )

// Declaration of the "special" parkingSpots used in the application
@Suppress("SpellCheckingInspection")
object SpecialParkingSpots {
    // when there are no parkingSpots to display, likely when filters are too restrictive
    val noParkingSpots = ParkingSpotInfo(
        id = "dummy",
        name = "Geen parkeerplaatsen gevonden ...",
        houseNr = "",
        type = "",
        streetName = "",
        capacity = 0,
        infoText = "",
        lon = 0.0,
        lat = 0.0
    )

    // When the application starts, used in first recompositions of the home screen
    val startParkingSpot = ParkingSpotInfo(
        id = "dummy",
        name = "Even geduld ...",
        houseNr = "",
        type = "",
        streetName = "",
        capacity = 0,
        infoText = "",
        lon = 0.0,
        lat = 0.0
    )

    // Used as details when no parkingSpot is actually found
    val emptyParkingSpot = ParkingSpotInfo(
        id = "dummy",
        name = "",
        houseNr = "",
        type = "",
        streetName = "",
        capacity = 0,
        infoText = "",
        lon = 0.0,
        lat = 0.0
    )
}

