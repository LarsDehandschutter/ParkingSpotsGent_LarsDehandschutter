package examen.parkingspotsgent.fake

import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotLocationRepository
import examen.parkingspotsgent.model.ParkingspotLocations

class FakeNetworkParkingSpotLocationRepository(
    private val doctorApiService: FakeParkingSpotApiService
)  : ParkingSpotLocationRepository {

    override suspend fun getParkingSpotLocations(): ParkingspotLocations = doctorApiService.getParkingSpotLocations(
        apiEndpoint = "dummy",
        options = mapOf()
    )
    override suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo> = getParkingSpotLocations().results.map{
        ParkingSpotInfo(
            id = it.urid,
            name = it.naam,
            houseNr = it.huisnr ?: "",
            type = it.type,
            streetName = it.straatnaam,
            capacity = it.capaciteit,
            infoText = it.infotekst ?: "",
            lon = it.geoPoint2d.lon,
            lat = it.geoPoint2d.lat
        )
    }
}