package examen.parkingspotsgent.data

import kotlinx.coroutines.flow.Flow

interface ParkingSpotInfoRepository {

    fun getAllParkingSPotsStream(): Flow<List<ParkingSpotInfo>>

    suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo

    /**
     * Insert doctor in the data source
     */
    suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo)

    /**
     * Delete doctor from the data source
     */
    suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo)

    /**
     * Update doctor in the data source
     */
    suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo)
}

class OfflineParkingSPotInfoRepository(private val parkingSpotInfoDao: ParkingSpotInfoDao) : ParkingSpotInfoRepository{
    override fun getAllParkingSPotsStream(): Flow<List<ParkingSpotInfo>> = parkingSpotInfoDao.getAllParkingSpots()

    override suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo = parkingSpotInfoDao.getParkingSpot(id)

    override suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.insert(parkingSpot)

    override suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.delete(parkingSpot)

    override suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.update(parkingSpot)
}