package examen.parkingspotsgent.rtmodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RealTimeParkingSpot(
    val results: List<ResultRT>,
    @SerialName("total_count")
    val totalCount: Int
)