package examen.parkingspotsgent.rtmodel



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * realTimeParkingSpotInfo with a availablespaces, name, longitude, latitude.
 *
 * @property availablespaces the empty spaces of the parking spot.
 * @property freeparking is it free to park in this parking spot.
 * @property gentseFeesten accesible during gentse feesten?.
 * @property isopennow can you park now at the parking spot?
 * @property lastupdate the last moment the [availablespaces] is updated.
 * @property latitude the latitude coordinate of the parking spot.
 * @property Location contains a longitude and latitude coordinate.
 * @property longitude the longitude coordinate of the parking spot.
 * @property name the name of the parking spot.
 * @property numberofspaces the capacity of the parking spot.
 * @property occupancytrend
 * @property occupation the amount of free parking spaces in percentage.
 * @property openingtimesdescription description on when the parking spot is open.
 * @property operatorinformation the owner of the parking spot.
 * @property temporaryclosed is the parking spot closed for a period of time?
 * @property type the type of parkingSpot.
 * @property urllinkaddress the url of the parkingSpot.
 * @constructor creates a parkingSpot with a id, capacity, houseNr, infoText, name, streetName, type, lon, lat,
 * owner, infoText in english and french, gfFeesten, Parking, url.
 */
@Suppress("SpellCheckingInspection")
@Serializable
data class ResultRT(
    val availablespaces: Int,
    val freeparking: Int,
    @SerialName("gentse_feesten")
    val gentseFeesten: String,
    val isopennow: Int,
    val lastupdate: String,
    val latitude: String,
    val location: Location,
    val longitude: String,
    val name: String,
    val numberofspaces: Int,
    val occupancytrend: String,
    val occupation: Int,
    val openingtimesdescription: String,
    val operatorinformation: String,
    val temporaryclosed: Int,
    val type: String,
    val urllinkaddress: String
)