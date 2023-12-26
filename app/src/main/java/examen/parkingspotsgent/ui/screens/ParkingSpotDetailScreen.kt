package examen.parkingspotsgent.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import examen.parkingspotsgent.ParkingSpotTopAppBar
import examen.parkingspotsgent.R
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.navigation.NavigationDestination
import examen.parkingspotsgent.ui.theme.ParkingspotsGentTheme

object ParkingSpotDetailsDestination : NavigationDestination {
    override val route = "parkingSpot_details"
    override val titleRes = R.string.parkingSpot_detail_title
  //  const val itemIdArg = "itemId"
  //  val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingSpotDetailsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ParkingSpotsViewModel
) {
    val appUiState = viewModel.appUiState.collectAsState()
    Scaffold(
        topBar = {
            ParkingSpotTopAppBar(
                title = stringResource(ParkingSpotDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = navigateBack,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        ParkingSpotDetailsBody(
            appUiState = appUiState.value,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun ParkingSpotDetailsBody(
    appUiState: AppUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        val parkingSpot = appUiState.selectedParkingSpot
        ParkingSpotDetails(
            parkingSpot = parkingSpot, modifier = Modifier.fillMaxWidth()
        )

    }
}




@Composable
fun ParkingSpotDetails(
    parkingSpot: ParkingSpotInfo?, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ParkingSpotDetailsRow(
                labelResID = R.string.parkingSpot,
                parkingSpotDetail = parkingSpot?.name ?: "",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.street,
                parkingSpotDetail = parkingSpot?.streetName ?: "",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.house_number,
                parkingSpotDetail = parkingSpot?.houseNr ?: "",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.type,
                parkingSpotDetail = parkingSpot?.type ?: "",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.capacity,
                parkingSpotDetail = parkingSpot?.capacity.toString() ?: "",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
        }

    }
}

@Composable
private fun ParkingSpotDetailsRow(
    @StringRes labelResID: Int, parkingSpotDetail: String?, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = "${stringResource(labelResID)}:")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = parkingSpotDetail ?: "", fontWeight = FontWeight.Bold)
    }
}


@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    ParkingspotsGentTheme {
        ParkingSpotDetailsBody(AppUiState())
    }
}