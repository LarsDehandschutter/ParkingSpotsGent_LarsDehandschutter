package examen.parkingspotsgent.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ParkingSpotInfoDao {


    @Query("SELECT* from parkingSpots")
    fun getAllParkingSpots(): Flow<List<ParkingSpotInfo>>

    @Query("SELECT * from parkingSpots WHERE id = :id")
    suspend fun getParkingSpot(id: String): ParkingSpotInfo

    // Retrieve the list of distinct types
    @Query("SELECT DISTINCT type from parkingSpots")
    suspend fun getTypes(): List<String>

    // Retrieve the list of primary keys which are distinct by nature
    @Query("SELECT id from parkingSpots")
    suspend fun getKeys(): List<String>

    // Room convenience function for insertion
    // Specify the conflict strategy as REPLACE, when the user tries to add an
    // existing parkingSpot into the database. Room solves the conflict by replacement.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parkingSpotInfo: ParkingSpotInfo)

    // Room convenience function for updates
    @Update
    suspend fun update(parkingSpotInfo: ParkingSpotInfo)

    // Room convenience function for deletion
    @Delete
    suspend fun delete(parkingSpotInfo: ParkingSpotInfo)
}