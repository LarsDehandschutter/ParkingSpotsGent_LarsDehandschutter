package examen.parkingspotsgent



import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import examen.parkingspotsgent.data.AppContainer
import examen.parkingspotsgent.data.DefaultAppContainer
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotLocationRepository

import examen.parkingspotsgent.ui.screens.ParkingSpotsViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun ParkingSpotApp(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
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
}