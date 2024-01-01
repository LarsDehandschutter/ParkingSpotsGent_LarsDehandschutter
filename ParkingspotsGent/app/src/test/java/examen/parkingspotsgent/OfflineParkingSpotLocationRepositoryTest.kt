package examen.parkingspotsgent

import examen.parkingspotsgent.data.OfflineParkingSpotInfoRepository
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.fake.FakeDataSource
import examen.parkingspotsgent.fake.FakeParkingSpotInfoDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class OfflineParkingSpotLocationRepositoryTest {
    @Test
    fun offlineParkingSpotInfoRepository_getAllParkingSpotStream_verifyParkingSpotStream() =
        runTest {
            /**
             * Use real OffLineParkingSpotInfoRepository with fake Dao injected
             * Provides effectively a fake repository
             */
            val repository = OfflineParkingSpotInfoRepository(
                parkingSpotInfoDao = FakeParkingSpotInfoDao()
            )
            assertEquals(
                FakeDataSource.parkingSpotLocations.results.map {
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
                },
                repository.getAllParkingSpotsStream().first() // Collects the first (and single) emit
            )
        }

    @Test
    fun offlineParkingSpotInfoRepository_getParkingSpot_verifyParkingSpot() =
        runTest {
            /**
             * Use real OffLineParkingSpotInfoRepository with fake Dao injected
             * Provides effectively a fake repository
             */
            val repository = OfflineParkingSpotInfoRepository(
                parkingSpotInfoDao = FakeParkingSpotInfoDao()
            )

            /**
             * Take first parkingSpot from fake data source
             */
            val parkingSpot = FakeDataSource.parkingSpotLocations.results.map {
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
            }[0]
            assertEquals(
                parkingSpot,
                repository.getParkingSpotsInfo(parkingSpot.id)
            )
        }
    @Test
    fun offlineParkingSpotInfoRepository_getTypes_verifyTypes() =
        runTest {
            /**
             * Use real OffLineParkingSpotInfoRepository with fake Dao injected
             * Provides effectively a fake repository
             */
            val repository = OfflineParkingSpotInfoRepository(
                parkingSpotInfoDao = FakeParkingSpotInfoDao()
            )

            /**
             * Build list of types from fake data source
             */
            val types = FakeDataSource.parkingSpotLocations.results.map {
                it.type
            }
            assertEquals(
                types,
                repository.getTypes()
            )
        }

    @Test
    fun offlineParkingSpotInfoRepository_getKeys_verifyKeys() =
        runTest {
            /**
             * Use real OffLineParkingSpotInfoRepository with fake Dao injected
             * Provides effectively a fake repository
             */
            val repository = OfflineParkingSpotInfoRepository(
                parkingSpotInfoDao = FakeParkingSpotInfoDao()
            )

            /**
             * Build list of keys from fake data source
             */
            val keys = FakeDataSource.parkingSpotLocations.results.map {
                it.urid
            }
            assertEquals(
                keys,
                repository.getKeys()
            )
        }
}