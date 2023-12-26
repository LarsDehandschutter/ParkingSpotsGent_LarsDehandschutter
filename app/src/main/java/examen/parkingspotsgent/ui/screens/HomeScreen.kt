package examen.parkingspotsgent.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import examen.parkingspotsgent.ParkingSpotTopAppBar
import examen.parkingspotsgent.R
import examen.parkingspotsgent.data.ParkingSpotInfo
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
    navigateToDetails: (String) -> Unit
){
    val parkingSpotUiState by viewModel.parkingSpotUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ParkingSpotTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },

    ) { innerPadding ->

        HomeBody(
            parkingSpotList = parkingSpotUiState.parkingSpotList,
            onItemClick = navigateToDetails,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
private fun HomeBody(
    parkingSpotList: List<ParkingSpotInfo>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        parkingSpotList(
            parkingSpotList = parkingSpotList,
            onItemClick = { onItemClick(it.id) },
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun parkingSpotList(
    parkingSpotList: List<ParkingSpotInfo>,
    onItemClick: (ParkingSpotInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {


        items(items = parkingSpotList, key = { it.id }) { parkingSpot ->
            ParkingSpotItem(
                parkingSpot = parkingSpot,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(parkingSpot) }
            )
        }
    }
}

@Composable
private fun ParkingSpotItem(
    parkingSpot: ParkingSpotInfo, modifier: Modifier = Modifier
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
                text = parkingSpot.capacity.toString(),
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
            listOf(
                ParkingSpotInfo(
                    id = "1",
                    capacity = 20,
                    houseNr = null,
                    infoText = null,
                    lon = 1.0,
                    lat = 1.0,
                    name = "Gent-Sint-Pieters",
                    streetName ="stationstraat",
                    type = "P"
                ),
                ParkingSpotInfo(
                    id = "2",
                    capacity = 50,
                    houseNr = null,
                    infoText = null,
                    lon = 1.0,
                    lat = 1.0,
                    name = "BommelstraatParking",
                    streetName ="Bommelstraat",
                    type = "P"
                ),
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorItemPreview() {
    ParkingspotsGentTheme {
        ParkingSpotItem(
            ParkingSpotInfo(
                id = "1",
                capacity = 20,
                houseNr = null,
                infoText = null,
                lon = 1.0,
                lat = 1.0,
                name = "Gent-Sint-Pieters",
                streetName ="stationstraat",
                type = "P"
            )
        )
    }
}

