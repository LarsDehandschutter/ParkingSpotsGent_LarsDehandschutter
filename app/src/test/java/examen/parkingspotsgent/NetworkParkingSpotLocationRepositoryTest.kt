package examen.parkingspotsgent

import examen.parkingspotsgent.data.NetworkParkingSpotLocationsRepository
import examen.parkingspotsgent.fake.FakeDataSource
import examen.parkingspotsgent.fake.FakeParkingSpotApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkParkingSpotLocationRepositoryTest {
    @Test
    fun networkParkingSpotLocationRepository_getParkingSpotLocations_verifyParkingSpotLocations() =
        runTest {
            val repository = NetworkParkingSpotLocationsRepository(
                parkingSpotsApiService = FakeParkingSpotApiService()
            )
            assertEquals(FakeDataSource.parkingSpotLocations, repository.getParkingSpotLocations())
        }
}