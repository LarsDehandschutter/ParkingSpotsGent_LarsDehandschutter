package examen.parkingspotsgent.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val geometry: GeometryX,
    val properties: Properties?,
    val type: String
)