package examen.parkingspotsgent.rtmodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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