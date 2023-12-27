package examen.parkingspotsgent.data

import kotlinx.coroutines.flow.Flow

interface ParkingSpotInfoRepository {

    fun getAllParkingSpotsStream(): Flow<List<ParkingSpotInfo>>

    suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo

    /**
     * Insert parkingSpot in the data source
     */
    suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo)

    /**
     * Delete parkingSpot from the data source
     */
    suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo)

    /**
     * Update parkingSpot in the data source
     */
    suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo)
}

class OfflineParkingSpotInfoRepository(private val parkingSpotInfoDao: ParkingSpotInfoDao) : ParkingSpotInfoRepository{
    override fun getAllParkingSpotsStream(): Flow<List<ParkingSpotInfo>> = parkingSpotInfoDao.getAllParkingSpots()

    override suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo = parkingSpotInfoDao.getParkingSpot(id)

    override suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.insert(parkingSpot)

    override suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.delete(parkingSpot)

    override suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.update(parkingSpot)
}