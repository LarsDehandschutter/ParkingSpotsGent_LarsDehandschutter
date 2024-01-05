package examen.parkingspotsgent.fake

import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotInfoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeParkingSpotInfoDao : ParkingSpotInfoDao {
    /**
     * Build list of parking spots from FakeDataSource
     */
    private val parkingSpotInfoList = FakeDataSource.parkingSpotLocations.results.map {
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

    /**
     * Build a flow and emit once
     */
    override fun getAllParkingSpots(): Flow<List<ParkingSpotInfo>> = flow {
        emit(parkingSpotInfoList)
    }

    override suspend fun getParkingSpot(id: String): ParkingSpotInfo {
        return parkingSpotInfoList.filter { it.id == id }[0]
    }

    override suspend fun getTypes(): List<String> {
        return parkingSpotInfoList.map { it.type }
    }

    override suspend fun getKeys(): List<String> {
        return parkingSpotInfoList.map { it.id }
    }

    /**
     * provide empty implementation for the convenience functions
     */
    override suspend fun insert(parkingSpotInfo: ParkingSpotInfo) = Unit

    override suspend fun delete(parkingSpotInfo: ParkingSpotInfo) = Unit

    override suspend fun update(parkingSpotInfo: ParkingSpotInfo) = Unit
}
