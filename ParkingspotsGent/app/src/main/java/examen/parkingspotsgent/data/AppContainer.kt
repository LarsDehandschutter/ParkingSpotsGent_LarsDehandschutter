package examen.parkingspotsgent.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import examen.parkingspotsgent.network.ParkingSpotsApiService
import examen.parkingspotsgent.network.RealTimeParkingSpotApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
        val parkingSpotLocationsRepository: ParkingSpotLocationRepository
    val parkingSpotInfoRepository: ParkingSpotInfoRepository
    }


    /**
     * Implementation for the Dependency Injection container at the application level.
     *
     * Variables are initialized lazily and the same instance is shared across the whole app.
     */
    class DefaultAppContainer(private val context: Context) : AppContainer {
        private val baseUrl = "https://data.stad.gent/api/explore/v2.1/catalog/datasets/"

        /**
         * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
         */
        private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()

        /**
         * Retrofit service object for creating api calls
         */
        private val retrofitService: ParkingSpotsApiService by lazy {
            retrofit.create(ParkingSpotsApiService::class.java)
        }
        private val retrofitServiceRT: RealTimeParkingSpotApiService by lazy {
            retrofit.create(RealTimeParkingSpotApiService::class.java)
        }

        /**
         * DI implementation for parkingSpot location repository
         */
        override val parkingSpotLocationsRepository: ParkingSpotLocationRepository by lazy {
            NetworkParkingSpotLocationsRepository(retrofitService, retrofitServiceRT)
        }
        /**
         * DI Implementation for [ParkingSpotInfoRepository]
         */
        override val parkingSpotInfoRepository: ParkingSpotInfoRepository by lazy {
            OfflineParkingSpotInfoRepository(ParkingSpotsDatabase.getDatabase(context).parkingSpotInfoDao())
        }
    }


