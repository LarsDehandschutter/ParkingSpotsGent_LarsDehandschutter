package examen.parkingspotsgent.network



import examen.parkingspotsgent.rtmodel.RealTimeParkingSpot
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RealTimeParkingSpotApiService {
    /**
     * Returns a [RealTimeParkingSpot] object and this method can be called from a Coroutine.
     * The @GET, @Path and @QueryMap annotation indicate that a dynamic endpoint will be requested with the GET
     * HTTP method. The endpoint is provided as an argument of the method, as well as appropriate
     * query options.
     */
    @GET("{apiEndpoint}")
    suspend fun getRealTimeParkingSpot(@Path("apiEndpoint")apiEndpoint :String, @QueryMap options: Map<String, String>): RealTimeParkingSpot
}