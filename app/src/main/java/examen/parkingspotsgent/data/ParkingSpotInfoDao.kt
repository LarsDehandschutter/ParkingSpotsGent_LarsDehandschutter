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

    /**
     * Retrieve All parkingSpots
     * @return a list with all parkingSpots
     */

    @Query("SELECT* from parkingSpots")
    fun getAllParkingSpots(): Flow<List<ParkingSpotInfo>>

    /**
     * Retrieve one parkingSpot based on his primary key
     * @param id the id of the parkingSpot
     * @return the parkingSpot with the correct id
     */
    @Query("SELECT * from parkingSpots WHERE id = :id")
    suspend fun getParkingSpot(id: String): ParkingSpotInfo
    /**
     * Retrieve the list of distinct types
     * @return all the distinct types that occur in the parkingSpots
     */
    @Query("SELECT DISTINCT type from parkingSpots")
    suspend fun getTypes(): List<String>

    /**
     * Retrieve the list of primary keys which are distinct by nature
     * @return all the the id of the parkingSpots
     */
    @Query("SELECT id from parkingSpots")
    suspend fun getKeys(): List<String>

    /**
     * Insert a [ParkingSpotInfo] to the database.
     */
    /**
     *  Room convenience function for insertion
     *  Specify the conflict strategy as REPLACE, when the user tries to add an
     *  existing parkingSpot into the database. Room solves the conflict by replacement.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parkingSpotInfo: ParkingSpotInfo)

    /**
     * Update a [ParkingSpotInfo] to the database.
     */
    @Update
    suspend fun update(parkingSpotInfo: ParkingSpotInfo)

    /**
     * Delete a [ParkingSpotInfo] to the database.
     */
    @Delete
    suspend fun delete(parkingSpotInfo: ParkingSpotInfo)
}