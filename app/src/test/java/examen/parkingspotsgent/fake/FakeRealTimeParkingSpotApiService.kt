package examen.parkingspotsgent.fake



import examen.parkingspotsgent.network.RealTimeParkingSpotApiService
import examen.parkingspotsgent.rtmodel.RealTimeParkingSpot

class FakeRealTimeParkingSpotApiService : RealTimeParkingSpotApiService {
    override suspend fun getRealTimeParkingSpot(
        apiEndpoint :String,
        options: Map<String, String>
    ): RealTimeParkingSpot {
        return if (options["offset"] == "0")
            FakeDataSource.realTimeParkingSpotLocations
        else
            RealTimeParkingSpot(totalCount = 0, results = listOf())
    }
}