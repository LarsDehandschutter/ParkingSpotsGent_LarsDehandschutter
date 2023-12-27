package examen.parkingspotsgent.data

import kotlinx.coroutines.flow.Flow

interface ParkingSpotInfoRepository {

    fun getAllParkingSpotsStream(): Flow<List<ParkingSpotInfo>>

    suspend fun getParkingSpotsInfo(id: String): ParkingSpotInfo

    // Retrieve the list of distinct types
    suspend fun getTypes(): List<String>

    // Retrieve the list of primary keys which are distinct by nature
    suspend fun getKeys(): List<String>

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

    override suspend fun getTypes(): List<String> = parkingSpotInfoDao.getTypes()

    override suspend fun getKeys(): List<String> = parkingSpotInfoDao.getKeys()

    override suspend fun insertParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.insert(parkingSpot)

    override suspend fun deleteParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.delete(parkingSpot)

    override suspend fun updateParkingSpot(parkingSpot: ParkingSpotInfo) = parkingSpotInfoDao.update(parkingSpot)
}