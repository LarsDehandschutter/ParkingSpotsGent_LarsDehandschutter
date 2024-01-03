package examen.parkingspotsgent

import examen.parkingspotsgent.data.NetworkParkingSpotLocationsRepository
import examen.parkingspotsgent.fake.FakeDataSource
import examen.parkingspotsgent.fake.FakeNetworkParkingSpotLocationRepository
import examen.parkingspotsgent.fake.FakeParkingSpotApiService
import examen.parkingspotsgent.fake.FakeRealTimeParkingSpotApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkParkingSpotLocationRepositoryTest {
    @Test
    fun networkParkingSpotLocationRepository_getParkingSpotLocations_verifyParkingSpotLocations() =
        runTest {
            val repository = NetworkParkingSpotLocationsRepository(
                parkingSpotsApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
            assertEquals(FakeDataSource.parkingSpotLocations, repository.getParkingSpotLocations())
        }
    @Test
    fun networkParkingSpotLocationRepository_getRealTimeParkingSpotLocations_verifyRealTimeParkingSpotLocations()=
        runTest {
            val repository = FakeNetworkParkingSpotLocationRepository(
                parkingSpotApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
            assertEquals(FakeDataSource.realTimeParkingSpotLocations,repository.getRealTimeParkingSpot())
        }
}