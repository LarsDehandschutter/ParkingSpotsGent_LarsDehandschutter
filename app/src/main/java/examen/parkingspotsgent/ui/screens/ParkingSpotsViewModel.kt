package examen.parkingspotsgent.ui.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import examen.parkingspotsgent.ParkingSpotsLocationApplication
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotInfoRepository
import examen.parkingspotsgent.data.ParkingSpotLocationRepository
import examen.parkingspotsgent.data.RealTimeParkingSpotInfo
import examen.parkingspotsgent.data.SpecialParkingSpots
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import retrofit2.HttpException
import java.io.IOException


data class ParkingSpotsUiState(val parkingSpotList: List<ParkingSpotInfo> = listOf())

data class AppUiState(
    /**
     * types to display in Ui
     */
    val typeFilter: MutableSet<String> = mutableSetOf(),
    /**
     * Parking spot selection for details
     */
    val selectedParkingSpot: ParkingSpotInfo = SpecialParkingSpots.noParkingSpots,
    /**
     * true when online and offline data fully synchronized
     */
    val synchronized: Boolean = false,

    val refreshCount: Long = 0
)
class ParkingSpotsViewModel(
    val parkingSpotInfoRepository : ParkingSpotInfoRepository,
    val parkingSpotLocationRepository: ParkingSpotLocationRepository
): ViewModel() {
    val parkingSpotUiState: StateFlow<ParkingSpotsUiState> =
        parkingSpotInfoRepository.getAllParkingSpotsStream().map{ ParkingSpotsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ParkingSpotsUiState()
            )

    private val _uiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    /**
     * all types, initially empty
     */
    val types = mutableSetOf<String>()

    /**
     * flagging successful retrofit
     */
    var retrofitSuccessful: Boolean = false

    var realTimeParkingSpotInfo: List<RealTimeParkingSpotInfo> = mutableListOf()

    /**
     * Retrofit, Room database sync, sets type filters to all
     */
    private fun getAllParkingSpots() = viewModelScope.launch {
        updateFilters()
        val parkingSpots =
            try {
                parkingSpotLocationRepository.getAllParkingSpotInfo()
            } catch (e: IOException) {
                listOf()
            } catch (e: HttpException) {
                listOf()
            }
            val primaryKeys = parkingSpotInfoRepository.getKeys().toMutableSet()
            if (parkingSpots.isNotEmpty()) {
                retrofitSuccessful = true
                // Check if retrofitted parkingSpots are identical in Room database
                parkingSpots.forEach {
                    val unchanged = when {
                        primaryKeys.contains(it.id) -> it == parkingSpotInfoRepository.getParkingSpotsInfo(it.id)
                        else -> false
                    }
                    if (!unchanged)
                        parkingSpotInfoRepository.insertParkingSpot(it) // replace with retrofitted parkingSpot
                    primaryKeys.remove(it.id) // Remove primary key for each retrofitted parkingSpot
                }
                // Remove parkingSpots corresponding to remaining keys in primary keys set
                primaryKeys.forEach {
                    parkingSpotInfoRepository.deleteParkingSpot(parkingSpotInfoRepository.getParkingSpotsInfo(it))
                }
            } else {
                retrofitSuccessful = false
            }
        updateFilters()
        // Retrofit/Room synchronization now completed, update state for recomposition
        _uiState.update { currentState ->
            currentState.copy(synchronized = true)
        }
    }

    /**
     * Sets type filter to allow all types
     */
    private suspend fun updateFilters() {
        parkingSpotInfoRepository.getTypes().forEach {
            types.add(it)
        }
        setTypeFilter(types)
    }

    /**
     * Selects a parkingSpot for state based on primary key string
     */
    fun selectParkingSpot(id: String) = viewModelScope.launch {
        val parkingSpot = parkingSpotInfoRepository.getParkingSpotsInfo(id)
        _uiState.update { currentState ->
            currentState.copy(selectedParkingSpot = parkingSpot)
        }
    }
    /**
     * Set the filter for type for this app's state.
     */
    fun setTypeFilter(filter: MutableSet<String>) {
        _uiState.update { currentState ->
            currentState.copy(typeFilter = filter)
        }
    }

    /**
     * Sets de selected parkingSpot in state to the empty parkingSpot (no information)
     */
    fun clearParkingSpot() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedParkingSpot = SpecialParkingSpots.emptyParkingSpot
            )
        }
    }

    /**
     * Starts the real time monitoring of the P+R parking spots and store in the view model
     */
    private fun startRealTimeParkingMonitor() = viewModelScope.launch {
        // Trigger the flow and consume its elements using collect
        parkingSpotLocationRepository.realTimeParking
            .catch { }
            .collect {
                realTimeParkingSpotInfo = it.toList()
                _uiState.update { currentState ->
                    currentState.copy(
                        refreshCount = currentState.refreshCount + 1
                    )
                }
            }
    }

    /**
     * During init of single view model, retrofit all parkingSpots, sync Room database
     * and set type filters
     */
    init {
        getAllParkingSpots()
        startRealTimeParkingMonitor()
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ParkingSpotsLocationApplication)
                val parkingSpotInfoRepository = application.container.parkingSpotInfoRepository
                val parkingSpotLocationsRepository = application.container.parkingSpotLocationsRepository
                ParkingSpotsViewModel(parkingSpotInfoRepository = parkingSpotInfoRepository, parkingSpotLocationRepository = parkingSpotLocationsRepository )
            }
        }
    }
}