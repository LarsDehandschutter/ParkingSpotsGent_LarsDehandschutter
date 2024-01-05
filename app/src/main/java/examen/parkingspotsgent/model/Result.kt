package examen.parkingspotsgent.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * parkingSpotInfo with a id, capacity, houseNr, infoText, name, streetName, type, lon, lat.
 *
 * @property urid the id of the parkingSpot.
 * @property capaciteit the amount of spaces of the parkingSpot.
 * @property huisnr the house number of the parkingSpot.
 * @property infotekst extra info about the parkingSpot.
 * @property naam the name of the parkingSpot.
 * @property straatnaam the name of the street where the parkingSPot is located.
 * @property type the type of parkingSpot.
 * @property GeoPoint2d contains a longitude and latitude coordinate.
 * @property Geometry the location of the parkingSpot.
 * @property eigenaar has the parkingSpot an owner?
 * @property enginfotekst extra info about the parkingSpot in english.
 * @property frinfotekst extra info about the parkingSpot in french.
 * @property gfFeesten accesible during gentse feesten?
 * @property parking short version of type.
 * @property url the url of the parkingSpot.
 * @constructor creates a parkingSpot with a id, capacity, houseNr, infoText, name, streetName, type, lon, lat,
 * owner, infoText in english and french, gfFeesten, Parking, url.
 */
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