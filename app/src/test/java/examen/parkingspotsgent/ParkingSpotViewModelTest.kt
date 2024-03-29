package examen.parkingspotsgent

import examen.parkingspotsgent.components.TestDispatcherRule
import examen.parkingspotsgent.data.OfflineParkingSpotInfoRepository
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.RealTimeParkingSpotInfo
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.fake.FakeDataSource
import examen.parkingspotsgent.fake.FakeNetworkParkingSpotLocationRepository
import examen.parkingspotsgent.fake.FakeParkingSpotApiService
import examen.parkingspotsgent.fake.FakeParkingSpotInfoDao
import examen.parkingspotsgent.fake.FakeRealTimeParkingSpotApiService
import examen.parkingspotsgent.ui.screens.ParkingSpotsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ParkingSpotViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun parkingSpotViewModel_getAllParkingSpots_verifyViewModelInitSuccess() = runTest {
        /**
         * The fake offline repository is build by using the real repository and injecting it
         * with fake dao
         * The network repository used is the fake one
         */
        val parkingSpotViewModel = ParkingSpotsViewModel(
            parkingSpotInfoRepository = OfflineParkingSpotInfoRepository(parkingSpotInfoDao = FakeParkingSpotInfoDao()),
            parkingSpotLocationRepository = FakeNetworkParkingSpotLocationRepository(
                parkingSpotApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
        )
        val types = parkingSpotViewModel.types
        val typeFilter = parkingSpotViewModel.appUiState.value.typeFilter
        val selectedParkingSpot = parkingSpotViewModel.appUiState.value.selectedParkingSpot
        val parkingSpotList = parkingSpotViewModel.parkingSpotUiState.first().parkingSpotList // first (and single) emit is collected
        val realTimeParkingSpotInfo = parkingSpotViewModel.realTimeParkingSpotInfo
        /**
         * Check if retrofit was successful without exception thrown
         */
        assertTrue(parkingSpotViewModel.retrofitSuccessful)
        /**
         * Check if all types are stored in view model
         */
        assertEquals(
            types,
            FakeDataSource.parkingSpotLocations.results.map { it.type }.toMutableSet()
        )
        /**
         * Check if type filters contains all types
         */
        assertEquals(types, typeFilter)
        /**
         * Check if the initial selected parkingSpot is "special"
         */
        assertEquals(selectedParkingSpot, SpecialParkingSpots.noParkingSpots)
        /**
         * Check if the parkingSpot list corresponds to the fake data source
         */
        assertEquals(
            parkingSpotList,
            FakeDataSource.parkingSpotLocations.results.map {
                ParkingSpotInfo(
                    id = it.urid,
                    name = it.naam,
                    houseNr = it.huisnr ?: "",
                    type = it.type,
                    streetName = it.straatnaam,
                    capacity = it.capaciteit,
                    infoText = it.infotekst ?: "",
                    lon = it.geoPoint2d.lon,
                    lat = it.geoPoint2d.lat
                )
            }
        )
        assertEquals(
            realTimeParkingSpotInfo,
            FakeDataSource.realTimeParkingSpotLocations.results.map {
                RealTimeParkingSpotInfo(
                    name = it.name,
                    availableSpaces = it.availablespaces,
                    lon = it.longitude,
                    lat = it.latitude
                )
            }
        )
    }

    @Test
    fun parkingSpotViewModel_setTypeFilter_verifyAppUiState() = runTest {
        /**
         * The fake offline repository is build by using the real repository and injecting it
         * with fake dao
         * The network repository used is the fake one
         */
        val parkingSpotViewModel = ParkingSpotsViewModel(
            parkingSpotInfoRepository = OfflineParkingSpotInfoRepository(parkingSpotInfoDao = FakeParkingSpotInfoDao()),
            parkingSpotLocationRepository = FakeNetworkParkingSpotLocationRepository(
                parkingSpotApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
        )
        /**
         * Build a type filter containing the type of the first parkingSpot in the fake data source
         */
        val typeFilter = mutableSetOf(FakeDataSource.parkingSpotLocations.results.map { it.type}[0])
        parkingSpotViewModel.setTypeFilter(typeFilter)
        assertEquals(parkingSpotViewModel.appUiState.value.typeFilter, typeFilter)
    }
    @Test
    fun parkingSpotViewModel_selectParkingSpot_verifyAppUiState() = runTest {
        /**
         * The fake offline repository is build by using the real repository and injecting it
         * with fake dao
         * The network repository used is the fake one
         */
        val parkingSpotViewModel = ParkingSpotsViewModel(
            parkingSpotInfoRepository = OfflineParkingSpotInfoRepository(parkingSpotInfoDao = FakeParkingSpotInfoDao()),
            parkingSpotLocationRepository = FakeNetworkParkingSpotLocationRepository(
                parkingSpotApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
        )
        /**
         * Take primary key of first parkingSpot in fake data source
         */
        val key = FakeDataSource.parkingSpotLocations.results.map { it.urid }[0]
        /**
         * Take first parkingSpot in fake data source
         */
        val parkingSpot = FakeDataSource.parkingSpotLocations.results.map {
            ParkingSpotInfo(
                id = it.urid,
                name = it.naam,
                houseNr = it.huisnr ?: "",
                type = it.type,
                streetName = it.straatnaam,
                capacity = it.capaciteit,
                infoText = it.infotekst ?: "",
                lon = it.geoPoint2d.lon,
                lat = it.geoPoint2d.lat
            )
        }[0]
        parkingSpotViewModel.selectParkingSpot(key)
        assertEquals(parkingSpotViewModel.appUiState.value.selectedParkingSpot, parkingSpot)
    }

    @Test
    fun parkingSpotViewModel_clearParkingSpot_verifyAppUiState() = runTest {
        /**
         * The fake offline repository is build by using the real repository and injecting it
         * with fake dao
         * The network repository used is the fake one
         */
        val parkingSpotViewModel = ParkingSpotsViewModel(
            parkingSpotInfoRepository = OfflineParkingSpotInfoRepository(parkingSpotInfoDao = FakeParkingSpotInfoDao()),
            parkingSpotLocationRepository = FakeNetworkParkingSpotLocationRepository(
                parkingSpotApiService = FakeParkingSpotApiService(),
                realTimeParkingSpotApiService = FakeRealTimeParkingSpotApiService()
            )
        )
        parkingSpotViewModel.clearParkingSpot()
        assertEquals(parkingSpotViewModel.appUiState.value.selectedParkingSpot, SpecialParkingSpots.emptyParkingSpot)
    }
}