package examen.parkingspotsgent.model



import kotlinx.serialization.Serializable

@Serializable
data class GeometryX(
    val coordinates: List<Double>,
    val type: String
)