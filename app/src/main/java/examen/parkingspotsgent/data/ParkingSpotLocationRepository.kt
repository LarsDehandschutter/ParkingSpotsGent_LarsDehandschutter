package examen.parkingspotsgent.data

import examen.parkingspotsgent.model.ParkingspotLocations
import examen.parkingspotsgent.model.Result
import examen.parkingspotsgent.network.ParkingSpotsApiService
import examen.parkingspotsgent.network.RealTimeParkingSpotApiService
import examen.parkingspotsgent.rtmodel.RealTimeParkingSpot
import examen.parkingspotsgent.rtmodel.ResultRT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository that fetches parkingSpot locations from ParkingSpotApi.
 */
interface ParkingSpotLocationRepository {
    /** Fetches parkingSpot locations from ParkingSpotApi */
    suspend fun getParkingSpotLocations(): ParkingspotLocations
    /** Fetches parkingSpot locations from Room database which is synchronized with ParkingSpotApi */
    suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo>

    suspend fun getRealTimeParkingSpot(): RealTimeParkingSpot
    /* Fetches real time parking information that is useful for the application*/
    suspend fun getRealTimeParkingSpotInfo(): List<RealTimeParkingSpotInfo>
    /* Flow that emits the real time parking info on a regular (60 s) basis*/
    val realTimeParking: Flow<List<RealTimeParkingSpotInfo>>
}

// Interval for refreshing real time parking information
private const val refreshIntervalMs: Long = 60000

// Custom exception used to cancel the flow in unit test
class TestException(message: String) : Exception(message)

/**
 * Network Implementation of Repository that fetches parkingSpot locations from ParkingSpotApi.
 */
@Suppress("SpellCheckingInspection")
class NetworkParkingSpotLocationsRepository(
    private val parkingSpotsApiService: ParkingSpotsApiService,
    private val realTimeParkingSpotApiService: RealTimeParkingSpotApiService
) : ParkingSpotLocationRepository {
    private val apiEndpoint = "locaties-openbare-parkings-gent/records"
    private val apiEndpointRealTime = "real-time-bezetting-pr-gent/records"
    private val limit: Int = 100
    private var offsetOne: Int = 0
    private val qMapOne get() = mutableMapOf(
        "limit" to limit.toString(),
        "offset" to offsetOne.toString()
    )

    private var offsetTwo: Int = 0
    private val qMapTwo get() = mutableMapOf(
        "limit" to limit.toString(),
        "offset" to offsetTwo.toString()
    )

    /** Fetches ParkingSpot locations from ParkingSpotApi according to apiEndpoint */
    override suspend fun getParkingSpotLocations(): ParkingspotLocations =
        parkingSpotsApiService.getParkingSpotLocations(apiEndpoint, qMapOne)

    override suspend fun getAllParkingSpotInfo(): List<ParkingSpotInfo> {
        val results: MutableList<Result> = mutableListOf()
        results += getParkingSpotLocations().results
        while (results.size == offsetOne + limit) {
            offsetOne += limit
            results += getParkingSpotLocations().results
        }
        val allParkingSpotInfo = results.map {
            ParkingSpotInfo(
                id = it.urid,
                name = it.naam,
                houseNr = it.huisnr ?: "Onbekend",
                type = it.type,
                streetName = it.straatnaam,
                infoText = it.infotekst ?: "Geen extra info",
                capacity =it.capaciteit,
                lon = it.geoPoint2d.lon,
                lat = it.geoPoint2d.lat
            )
        }
        return allParkingSpotInfo
    }

    override suspend fun getRealTimeParkingSpot(): RealTimeParkingSpot =
        realTimeParkingSpotApiService.getRealTimeParkingSpot(apiEndpointRealTime, qMapTwo)

    // Collects potentially multiple RealTimeParkingSpotApi queries
    // and maps real time parking data to the data of interest for the application
    override  suspend fun getRealTimeParkingSpotInfo(): List<RealTimeParkingSpotInfo> {
        val results: MutableList<ResultRT> =  mutableListOf()
        results += getRealTimeParkingSpot().results
        while ( results.size == offsetTwo + limit) {
            offsetTwo += limit
            results += getRealTimeParkingSpot().results
        }
        offsetTwo = 0
        val realTimeParking = results.map {
            RealTimeParkingSpotInfo(
                name = it.name,
                availableSpaces = it.availablespaces,
                lat = it.latitude,
                lon = it.longitude
            )
        }
        return realTimeParking
    }

    // Actually build the flow for emmiting the real time parking information
    override val realTimeParking: Flow<List<RealTimeParkingSpotInfo>> = flow {
        while (true) {
            try {
                val realTimeParking = getRealTimeParkingSpotInfo()
                emit(realTimeParking) // Emits the result of the request to the flow
            }
            catch (_: IOException) {
                delay(100)
                continue
            }
            catch (_: HttpException) {
                delay(100)
                continue
            }
            catch (e: TestException) { break }
            delay(timeMillis = refreshIntervalMs) // Suspends the coroutine for some time
        }
    }
}