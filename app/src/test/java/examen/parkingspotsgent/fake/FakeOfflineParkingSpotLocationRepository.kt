package examen.parkingspotsgent.fake

import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotInfoRepository
import kotlinx.coroutines.flow.Flow

class FakeOfflineParkingSpotLocationRepository(
    private val parkingSpotInfoDao: FakeParkingSpotInfoDao
) : ParkingSpotInfoRepository {
    override fun getAllParkingSpotsStream(): Flow<List<ParkingSpotInfo>> = parkingSpotInfoDao.getAllParkingSpots()

    override suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo = parkingSpotInfoDao.getParkingSpot(id)

    override suspend fun getTypes(): List<String> = parkingSpotInfoDao.getTypes()

    override suspend fun getKeys(): List<String> = parkingSpotInfoDao.getKeys()

    override suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.insert(parkingSpot)

    override suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.delete(parkingSpot)

    override suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.update(parkingSpot)
}
