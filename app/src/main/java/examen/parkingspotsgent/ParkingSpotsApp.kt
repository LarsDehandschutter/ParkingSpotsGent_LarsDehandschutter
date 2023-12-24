package examen.parkingspotsgent



import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import examen.parkingspotsgent.ui.screens.ParkingSpotsViewModel
@Composable
fun ParkingSpotApp(navController: NavHostController = rememberNavController()) {
    val parkingSpotsViewModel: ParkingSpotsViewModel =
        viewModel(factory = ParkingSpotsViewModel.Factory)
    val parkingSpotsUiState by parkingSpotsViewModel.parkingSpotUiState.collectAsState()
    LazyColumn {
        items(items = parkingSpotsUiState.parkingSpotList, key = { it.id }){ parkingSpotInfo ->
            Text(
                text = parkingSpotInfo.name,

                )

        }
    }
}
