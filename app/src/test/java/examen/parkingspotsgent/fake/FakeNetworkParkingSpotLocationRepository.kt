package examen.parkingspotsgent.fake

import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotLocationRepository
import examen.parkingspotsgent.data.RealTimeParkingSpotInfo
import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.rtmodel.RealTimeParkingSpot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNetworkParkingSpotLocationRepository(
    private val parkingSpotApiService: FakeParkingSpotApiService,
    private val realTimeParkingSpotApiService: FakeRealTimeParkingSpotApiService
)  : ParkingSpotLocationRepository {

    override suspend fun getParkingSpotLocations(): ParkingspotLocations =
        parkingSpotApiService.getParkingSpotLocations(
            apiEndpoint = "dummy",
            options = mapOf("offset" to "0")
        )
    override suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo> =
        getParkingSpotLocations().results.map{
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
    override suspend fun getRealTimeParkingSpot(): RealTimeParkingSpot =
        realTimeParkingSpotApiService.getRealTimeParkingSpot(
            apiEndpoint = "dummy",
            options = mapOf("offset" to "0")
        )
    override suspend fun getRealTimeParkingSpotInfo(): List<RealTimeParkingSpotInfo> =
        getRealTimeParkingSpot().results.map {
            RealTimeParkingSpotInfo(
                name = it.name,
                availableSpaces = it.availablespaces,
                lat = it.latitude,
                lon = it.longitude
            )
        }
    override val realTimeParking: Flow<List<RealTimeParkingSpotInfo>> = flow{
        emit(getRealTimeParkingSpot().results.map {
            RealTimeParkingSpotInfo(
                name = it.name,
                availableSpaces = it.availablespaces,
                lat = it.latitude,
                lon = it.longitude
            ) }
        )
    }
}