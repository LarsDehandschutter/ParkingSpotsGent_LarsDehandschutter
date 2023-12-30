package examen.parkingspotsgent

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotInfoDao
import examen.parkingspotsgent.data.ParkingSpotsDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@Suppress("SpellCheckingInspection")
@RunWith(AndroidJUnit4::class)
class ParkingSpotInfoDaoTest {
    private lateinit var parkingSpotInfoDao: ParkingSpotInfoDao
    private lateinit var parkingSpotsDatabase: ParkingSpotsDatabase
    /**
     * Declaration of first parkingSpot
     */
    private val parkingSpotInfoOne = ParkingSpotInfo(
        id = "1",
        name = "naam1",
        houseNr = "1",
        type = "Parking",
        streetName = "straat1",
        capacity = 50,
        infoText = "",
        lon = 1.0,
        lat = 1.0
    )

    /**
     * Declaration of second parkingSpot
     */
    private val parkingSpotInfoTwo = ParkingSpotInfo(
        id = "2",
        name = "naam2",
        houseNr = "2",
        type = "Park and Ride",
        streetName = "straat2",
        capacity = 200,
        infoText = "",
        lon = 2.0,
        lat = 2.0
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        parkingSpotsDatabase = Room.inMemoryDatabaseBuilder(context, ParkingSpotsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        parkingSpotInfoDao = parkingSpotsDatabase.parkingSpotInfoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        parkingSpotsDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsParkingSpotIntoDB() = runBlocking {
        addOneParkingSpotToDb()
        val allParkingSpots = parkingSpotInfoDao.getAllParkingSpots().first() // Collect first Room emit
        assertEquals(allParkingSpots[0], parkingSpotInfoOne)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllParkingSpots_returnsAllParkingSpotsFromDB() = runBlocking {
        addTwoParkingSpotsToDb()
        val allParkingSpots = parkingSpotInfoDao.getAllParkingSpots().first() // Collect first Room emit
        // Do not make assumptions about list order
        Assert.assertTrue(allParkingSpots.containsAll(listOf(parkingSpotInfoOne, parkingSpotInfoTwo)))
    }


    @Test
    @Throws(Exception::class)
    fun daoGetParkingSpot_returnsParkingSpotFromDB() = runBlocking {
        addOneParkingSpotToDb()
        val parkingSpotInfo = parkingSpotInfoDao.getParkingSpot( id = parkingSpotInfoOne.id)
        assertEquals(parkingSpotInfo, parkingSpotInfoOne)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetTypes_returnsTypesFromDB() = runBlocking {
        addTwoParkingSpotsToDb()
        val types = parkingSpotInfoDao.getTypes()
        // Do not make assumptions about list order
        Assert.assertTrue(types.containsAll(listOf(parkingSpotInfoOne.type, parkingSpotInfoTwo.type)))
    }

    @Test
    @Throws(Exception::class)
    fun daoGetKeys_returnsKeysFromDB() = runBlocking {
        addTwoParkingSpotsToDb()
        val parkingSpotKeys = parkingSpotInfoDao.getKeys()
        // Do not make assumptions about list order
        Assert.assertTrue(parkingSpotKeys.containsAll(listOf(parkingSpotInfoOne.id, parkingSpotInfoTwo.id)))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteParkingSpots_deletesAllParkingSpotsFromDB() = runBlocking {
        addTwoParkingSpotsToDb()
        parkingSpotInfoDao.delete(parkingSpotInfoOne)
        parkingSpotInfoDao.delete(parkingSpotInfoTwo)
        val allParkingSpots = parkingSpotInfoDao.getAllParkingSpots().first() // Collect first Room emit
        Assert.assertTrue(allParkingSpots.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateParkingSpots_updatesParkingSpotsInDB() = runBlocking {
        /**
         * Make sure the id primary key property is the same as for the first parkingSpot
         */
        val newParkingSpotInfoOne = parkingSpotInfoOne.copy(
            houseNr = "3",
            streetName = "straat1_update",
            lon = 3.0,
            lat = 3.0
        )

        /**
         * Make sure the id primary key property is the same as for the second parkingSpot
         */
        val newParkingSpotInfoTwo = parkingSpotInfoTwo.copy(
            houseNr = "4",
            streetName = "straat2_update",
            lon = 4.0,
            lat = 4.0
        )
        addTwoParkingSpotsToDb()
        parkingSpotInfoDao.update(newParkingSpotInfoOne)
        parkingSpotInfoDao.update(newParkingSpotInfoTwo)
        val allParkingSpots = parkingSpotInfoDao.getAllParkingSpots().first() // Collect first Room emit
        // Do not make assumptions about list order
        Assert.assertTrue(allParkingSpots.containsAll(listOf(newParkingSpotInfoOne, newParkingSpotInfoTwo)))
    }

    /**
     * Add first parkingSpot to database
     */
    private suspend fun addOneParkingSpotToDb() {
        parkingSpotInfoDao.insert(parkingSpotInfoOne)
    }

    /**
     * Add first and second parkingSpot to database
     */
    private suspend fun addTwoParkingSpotsToDb() {
        parkingSpotInfoDao.insert(parkingSpotInfoOne)
        parkingSpotInfoDao.insert(parkingSpotInfoTwo)
    }
}