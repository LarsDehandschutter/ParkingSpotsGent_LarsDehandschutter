package examen.parkingspotsgent.fake


import examen.parkingspotsgent.data.TestException
import examen.parkingspotsgent.network.RealTimeParkingSpotApiService
import examen.parkingspotsgent.rtmodel.RealTimeParkingSpot

class FakeRealTimeParkingSpotApiService : RealTimeParkingSpotApiService {
    override suspend fun getRealTimeParkingSpot(apiEndpoint :String,  options: Map<String, String>): RealTimeParkingSpot {
        throw TestException("End the flow")
    }
}