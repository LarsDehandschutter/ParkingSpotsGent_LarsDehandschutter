package examen.parkingspotsgent.network

import examen.parkingspotsgent.model.ParkingspotLocations
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface
ParkingSpotsApiService {
    @GET("{apiEndpoint}")
    suspend fun getParkingSpotLocations(@Path("apiEndpoint")apiEndpoint :String, @QueryMap options: Map<String, String>): ParkingspotLocations
}