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

    override suspend fun insertParkingSpot(doctor: ParkingSpotInfo) = parkingSpotInfoDao.insert(doctor)

    override suspend fun deleteParkingSpot(doctor: ParkingSpotInfo) = parkingSpotInfoDao.delete(doctor)

    override suspend fun updateParkingSpot(doctor: ParkingSpotInfo) = parkingSpotInfoDao.update(doctor)
}
