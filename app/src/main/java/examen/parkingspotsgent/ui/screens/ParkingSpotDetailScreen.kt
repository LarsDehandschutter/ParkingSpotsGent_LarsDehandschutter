package examen.parkingspotsgent.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.SoundEffectConstants
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import examen.parkingspotsgent.ParkingSpotTopAppBar
import examen.parkingspotsgent.R
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.navigation.NavigationDestination
import examen.parkingspotsgent.ui.theme.ParkingspotsGentTheme

object ParkingSpotDetailsDestination : NavigationDestination {
    override val route = "parkingSpot_details"
    override val titleRes = R.string.parkingSpot_detail_title
}

/**
 * Composable that displays the appbar, a detail body and a floating action button to go to google maps
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingSpotDetailsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ParkingSpotsViewModel
) {
    val appUiState = viewModel.appUiState.collectAsState()
    val parkingSpot = appUiState.value.selectedParkingSpot

    val view = LocalView.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ParkingSpotTopAppBar(
                title = stringResource(ParkingSpotDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            /**
             * Only provide a fab for showing the Google maps intent
             * if a real parkingSpot was selected
             */
            if (parkingSpot.id != SpecialParkingSpots.emptyParkingSpot.id) {
                FloatingActionButton(
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        showMap(
                            context = context,
                            lat = parkingSpot.lat,
                            lon = parkingSpot.lon
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = stringResource(R.string.show_map)
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ParkingSpotDetailsBody(
            parkingSpot = parkingSpot,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

/**
 * Composable that displays a column with the details body and button for google maps
 */
@VisibleForTesting
@Composable
internal fun ParkingSpotDetailsBody(
    parkingSpot: ParkingSpotInfo,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        ParkingSpotDetails(
            parkingSpot = parkingSpot, modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                showMap(
                    context = context,
                    lat = parkingSpot.lat,
                    lon = parkingSpot.lon
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            /**
             * Only enable the button if a real parking spot is detailed
             */
            enabled = parkingSpot.id != SpecialParkingSpots.emptyParkingSpot.id
        ) {
            Text(stringResource(R.string.show_map))
        }
    }
}

/**
 * intent to access google maps
 */
private fun showMap(context: Context, lat: Double, lon: Double) {
    val gmmIntentUri = Uri.parse("geo:$lat,$lon?z=18")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}

/**
 * Composable that shows all the details of the parking spot
 */
@Composable
fun ParkingSpotDetails(
    parkingSpot: ParkingSpotInfo, modifier: Modifier = Modifier
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
                parkingSpotDetail = parkingSpot.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.street,
                parkingSpotDetail = parkingSpot.streetName,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.house_number,
                parkingSpotDetail = parkingSpot.houseNr,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.type,
                parkingSpotDetail = parkingSpot.type,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ParkingSpotDetailsRow(
                labelResID = R.string.capacity,
                parkingSpotDetail = parkingSpot.capacity.toString(),
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

/**
 * Composable that shows one detail from a parking spot
 */
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
        ParkingSpotDetailsBody(SpecialParkingSpots.emptyParkingSpot)
    }
}