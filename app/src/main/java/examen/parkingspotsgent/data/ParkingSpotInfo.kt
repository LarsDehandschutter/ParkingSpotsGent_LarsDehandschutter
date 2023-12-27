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



