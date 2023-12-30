package examen.parkingspotsgent.fake

import examen.parkingspotsgent.model.GeoPoint2d
import examen.parkingspotsgent.model.Geometry
import examen.parkingspotsgent.model.GeometryX
import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.model.Result
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName

/**
 * Build a fake datasource with two parkingSpot locations (JSON serialized format)
 */
@Suppress("SpellCheckingInspection")
object FakeDataSource {
    private const val latOne = 1.0
    private const val latTwo = 2.0
    private const val lonOne = 1.0
    private const val lonTwo = 2.0
    private const val geoXType = "Point"
    private const val geoType = "Feature"
    private val geoPoint2dOne = GeoPoint2d(lat = latOne, lon = lonOne)
    private val geoPoint2dTwo = GeoPoint2d(lat = latTwo, lon = lonTwo)
    private val geometryXOne = GeometryX(coordinates = listOf(latOne, lonOne), type = geoXType)
    private val geometryXTwo = GeometryX(coordinates = listOf(latTwo, lonTwo), type = geoXType)
    private val geometryOne = Geometry(geometry = geometryXOne, properties = null, type = geoType)
    private val geometryTwo = Geometry(geometry = geometryXTwo, properties = null, type = geoType)

    private val resultOne = Result(
         capaciteit = 100,
         dashboard = "false",
         eigenaar = "ja",
         enginfotekst = null,
         frinfotekst = null,
         geoPoint2d = geoPoint2dOne,
         geometry = geometryOne,
         gfFeesten= "false",
         huisnr = "1",
         infotekst = null,
         naam = "naam1",
         parking= "P",
         parkingregime = null,
         straatnaam = "straat1",
         type = "Parking",
         urid = "urid1",
         url = "https://stad.gent/node/36642/"
    )
    private val resultTwo = Result(
        capaciteit = 500,
        dashboard = "false",
        eigenaar = "nee",
        enginfotekst = null,
        frinfotekst = null,
        geoPoint2d = geoPoint2dTwo,
        geometry = geometryTwo,
        gfFeesten= "false",
        huisnr = "2",
        infotekst = null,
        naam = "naam2",
        parking= "P+R",
        parkingregime = null,
        straatnaam = "straat2",
        type = "Park and Ride",
        urid = "urid2",
        url = "https://stad.gent/node/36642/"
    )
    val parkingSpotLocations = ParkingspotLocations( totalCount = 2, results = listOf(resultOne, resultTwo))
}