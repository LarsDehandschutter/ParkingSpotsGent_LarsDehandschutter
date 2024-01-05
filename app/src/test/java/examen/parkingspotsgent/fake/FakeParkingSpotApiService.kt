package examen.parkingspotsgent.fake

import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.network.ParkingSpotsApiService

class FakeParkingSpotApiService : ParkingSpotsApiService{
    /**
     * Allow for multiple invocations in case injected in real NetworkParkingSpotLocationRepository
     * Make sure the fake data source data is returned only when query option offset equals zero
     * This means only when it is the first invocation
     * This way an infinite loop is avoided if the size of the fake datasource would be changed to exactly 100 parkingSpot
     */
    override suspend fun getParkingSpotLocations(apiEndpoint :String, options: Map<String, String>): ParkingspotLocations {
        return if (options["offset"] == "0")
            FakeDataSource.parkingSpotLocations
        else
            ParkingspotLocations(totalCount = 0, results = listOf())
    }
}