package examen.parkingspotsgent.ui.screens

import android.icu.text.BreakIterator
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import examen.parkingspotsgent.ParkingSpotTopAppBar
import examen.parkingspotsgent.R
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.RealTimeParkingSpotInfo
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.navigation.NavigationDestination
import examen.parkingspotsgent.ui.theme.ParkingspotsGentTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ParkingSpotsViewModel,
    navigateToFilter: () -> Unit,
    navigateToDetails: (String) -> Unit

){
    val parkingSpotUiState by viewModel.parkingSpotUiState.collectAsState()
    val appUiState by viewModel.appUiState.collectAsState()
    val synchronized = appUiState.synchronized
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val realTimeParking = viewModel.realTimeParkingSpotInfo

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ParkingSpotTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToFilter,
                shape = MaterialTheme.shapes.medium,
                /**
                 * When Retrofit/Room synchronization is completed,
                 * put a test tag on the fab
                 */
                modifier = if (synchronized)
                    Modifier
                        .padding(dimensionResource(id = R.dimen.padding_large))
                        .testTag(stringResource(R.string.sync))
                else
                    Modifier
                        .padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.filter_title)
                )
            }
        },

    ) { innerPadding ->

        HomeBody(
            realTimeParkingList = realTimeParking,
            parkingSpotList = parkingSpotUiState.parkingSpotList,
            typeFilter = appUiState.typeFilter,
            onItemClick = navigateToDetails,
            synchronized = synchronized,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@VisibleForTesting
@Composable
internal fun HomeBody(
    realTimeParkingList: List<RealTimeParkingSpotInfo>,
    parkingSpotList: List<ParkingSpotInfo>,
    typeFilter: MutableSet<String>,
    onItemClick: (String) -> Unit,
    synchronized:  Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ParkingSpotList(
            realTimeParkingList = realTimeParkingList,
            parkingSpotList = parkingSpotList,
            typeFilter = typeFilter,
            onItemClick = { onItemClick(it.id) },
            synchronized = synchronized,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun ParkingSpotList(
    realTimeParkingList: List<RealTimeParkingSpotInfo>,
    parkingSpotList: List<ParkingSpotInfo>,
    typeFilter: MutableSet<String>,
    onItemClick: (ParkingSpotInfo) -> Unit,
    synchronized: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        /**
         * Filter the list of parkingSpots based on type filter sets
         */
        var filteredList = parkingSpotList.filter {
            typeFilter.contains(it.type)
        }

        /**
         * If the filtered list is empty, prepare to display appropriate "special" parkingSpot
         */
        val isEmptyList = filteredList.isEmpty()
        if (isEmptyList) {
            filteredList = if (synchronized)
                listOf(SpecialParkingSpots.noParkingSpots)
            else
                listOf(SpecialParkingSpots.startParkingSpot)
        }

        /**
         *  Display the filtered scrollable list
         */
        items(items = filteredList, key = { it.id }) { parkingSpot ->
            var availablePlaces : String? = "Niet beschikbaar"
            var korteLon = ""
            var lengteLon = 0
            realTimeParkingList.forEach{
                if(parkingSpot.id != "dummy") {
                     lengteLon = it.lon.length - 1
                    korteLon = parkingSpot.lon.toString().substring(0,lengteLon)
                }
                if(it.lon.substring(0,lengteLon) == korteLon) {
                    availablePlaces = it.availableSpaces.toString()
                }
                /*if(it.name.contains(parkingSpot.name)) {
                    availablePlaces = it.availableSpaces.toString()
                }*/

            }
            ParkingSpotItem(
                availablePlaces = availablePlaces,
                parkingSpot = parkingSpot,
                modifier = if(synchronized)
                    Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(parkingSpot) }
                    .testTag(stringResource(R.string.testItems))
                else
                    Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onItemClick(parkingSpot) }
            )
        }
    }
}

@Composable
private fun ParkingSpotItem(
    availablePlaces: String?,
    parkingSpot: ParkingSpotInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                    text = parkingSpot.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            Spacer(Modifier.weight(1f))
            Text(
                    text = parkingSpot.type,
                    style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                    text = "Beschikbare plaatsen: $availablePlaces",
                    style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    ParkingspotsGentTheme {
        HomeBody(
            listOf(),
            listOf(
                ParkingSpotInfo(
                    id = "1",
                    capacity = 20,
                    houseNr = "",
                    infoText = "",
                    lon = 1.0,
                    lat = 1.0,
                    name = "Gent-Sint-Pieters",
                    streetName ="stationstraat",
                    type = "P"
                ),
                ParkingSpotInfo(
                    id = "2",
                    capacity = 50,
                    houseNr = "",
                    infoText = "",
                    lon = 1.0,
                    lat = 1.0,
                    name = "BommelstraatParking",
                    streetName ="Bommelstraat",
                    type = "P"
                ),
            ),
            typeFilter = mutableSetOf(),
            onItemClick = {},
            synchronized = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingSpotItemPreview() {
    ParkingspotsGentTheme {
        ParkingSpotItem(
            availablePlaces = null,
            ParkingSpotInfo(
                id = "1",
                capacity = 20,
                houseNr = "",
                infoText = "",
                lon = 1.0,
                lat = 1.0,
                name = "Gent-Sint-Pieters",
                streetName ="stationstraat",
                type = "P"
            )
        )
    }
}

