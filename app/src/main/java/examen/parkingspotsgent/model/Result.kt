package examen.parkingspotsgent.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Suppress("SpellCheckingInspection")
@Serializable
data class Result(
    val capaciteit: Int,
    val dashboard: String,
    val eigenaar: String,
    val enginfotekst: String?,
    val frinfotekst: String?,
    @SerialName("geo_point_2d")
    val geoPoint2d: GeoPoint2d,
    val geometry: Geometry,
    @SerialName("gf_feesten")
    val gfFeesten: String,
    val huisnr: String?,
    val infotekst: String?,
    val naam: String,
    val parking: String,
    @Contextual
    val parkingregime: Any?,
    val straatnaam: String,
    val type: String,
    val urid: String,
    val url: String
)