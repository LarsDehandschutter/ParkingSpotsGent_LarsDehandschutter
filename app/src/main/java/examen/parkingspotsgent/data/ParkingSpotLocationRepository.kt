package examen.parkingspotsgent.data

import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.model.Result
import examen.parkingspotsgent.network.ParkingSpotsApiService
/**
 * Repository that fetches parkingSpot locations from ParkingSpotApi.
 */
interface ParkingSpotLocationRepository {
    /** Fetches parkingSpot locations from ParkingSpotApi */
    suspend fun getParkingSpotLocations(): ParkingspotLocations
    /** Fetches parkingSpot locations from Room database which is synchronized with ParkingSpotApi */
    suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo>
}
/**
 * Network Implementation of Repository that fetches parkingSpot locations from ParkingSpotApi.
 */
class NetworkParkingSpotLocationsRepository(
    private val parkingSpotsApiService: ParkingSpotsApiService
) : ParkingSpotLocationRepository {
    private val apiEndpoint = "locaties-openbare-parkings-gent/records"
    private var offset: Int = 0
    private val limit: Int = 100
    private val qMap
        get() = mutableMapOf(
            "limit" to limit.toString(),
            "offset" to offset.toString()
        )

    /** Fetches ParkingSpot locations from ParkingSpotApi according to apiEndpoint */
    override suspend fun getParkingSpotLocations(): ParkingspotLocations =
        parkingSpotsApiService.getParkingSpotLocations(apiEndpoint, qMap)

    override suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo> {
        val results: MutableList<Result> = mutableListOf()
        results += getParkingSpotLocations().results
        while (results.size == offset + limit) {
            offset += limit
            results += getParkingSpotLocations().results
        }
        val allParkingSpotInfo = results.map {
            ParkingSpotInfo(
                id = it.urid,
                name = it.naam,
                houseNr = it.huisnr ?: "onbekend",
                type = it.type,
                streetName = it.straatnaam,
                infoText = it.infotekst ?: "geen extra info",
                capacity =it.capaciteit,
                lon = it.geoPoint2d.lon,
                lat = it.geoPoint2d.lat
            )
        }
        return allParkingSpotInfo
    }
}