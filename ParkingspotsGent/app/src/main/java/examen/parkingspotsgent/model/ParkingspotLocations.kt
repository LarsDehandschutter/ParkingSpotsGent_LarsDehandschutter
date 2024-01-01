package examen.parkingspotsgent.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParkingspotLocations(
    val results: List<Result>,
    @SerialName("total_count")
    val totalCount: Int
)