package examen.parkingspotsgent




import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import examen.parkingspotsgent.data.AppContainer
import examen.parkingspotsgent.data.DefaultAppContainer
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotLocationRepository
import examen.parkingspotsgent.navigation.ParkingSpotNavHost

import examen.parkingspotsgent.ui.screens.ParkingSpotsViewModel
import kotlinx.coroutines.runBlocking



@Composable
fun ParkingSpotApp(navController: NavHostController = rememberNavController()) {
        val parkingSpotViewModel: ParkingSpotsViewModel =
            viewModel(factory = ParkingSpotsViewModel.Factory)
        ParkingSpotNavHost(navController = navController, viewModel = parkingSpotViewModel )
    }
/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingSpotTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


   /* val context = LocalContext.current
    val parkingSpotsViewModel: ParkingSpotsViewModel =
        viewModel(factory = ParkingSpotsViewModel.Factory)
    val parkingSpotsUiState by parkingSpotsViewModel.parkingSpotUiState.collectAsState()


    LazyColumn {
        items(items = parkingSpotsUiState.parkingSpotList, key = { it.id }){ parkingSpotInfo ->

            Text(
                text = parkingSpotInfo.name,
                modifier = Modifier
                    .clickable { showMap(
                        context = context,
                        lat = parkingSpotInfo.lat,
                        lon = parkingSpotInfo.lon,
                        label = parkingSpotInfo.name
                    )
                    }
            )

        }
    }
}
private fun showMap(context: Context, lat: Double, lon: Double, label: String) {
    val gmmIntentUri = Uri.parse("geo:$lat,$lon?z=18&q=$label")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}*/