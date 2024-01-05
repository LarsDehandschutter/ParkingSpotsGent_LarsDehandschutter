package examen.parkingspotsgent.network

import examen.parkingspotsgent.model.ParkingspotLocations
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

@Suppress("SpellCheckingInspection")
interface ParkingSpotsApiService {
    /**
     * function for creating retrofit api calls
     * @property apiEndpoint is the endpoint for the base url.
     * @constructor creates the retrofit connection
     * @return A [ParkingspotLocations] object and this method can be called from a Coroutine.
     * The @GET and @Path annotation indicate that a dynamic endpoint will be requested with the GET
     * HTTP method. The endpoint is provided as an argument of the method, as well as appropriate
     * query options.
     */
    @GET("{apiEndpoint}")
    suspend fun getParkingSpotLocations(@Path("apiEndpoint")apiEndpoint :String, @QueryMap options: Map<String, String>): ParkingspotLocations
}