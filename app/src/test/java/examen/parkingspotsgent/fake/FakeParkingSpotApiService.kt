package examen.parkingspotsgent.fake

import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.network.ParkingSpotsApiService

class FakeParkingSpotApiService : ParkingSpotsApiService{

    override suspend fun getParkingSpotLocations(apiEndpoint :String, options: Map<String, String>): ParkingspotLocations {
        return if (options["offset"] == "0")
            FakeDataSource.parkingSpotLocations
        else
            ParkingspotLocations(totalCount = 0, results = listOf())
    }
}