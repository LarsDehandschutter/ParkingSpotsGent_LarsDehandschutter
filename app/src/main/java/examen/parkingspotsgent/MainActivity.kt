package examen.parkingspotsgent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import examen.parkingspotsgent.data.AppContainer
import examen.parkingspotsgent.data.DefaultAppContainer
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.ParkingSpotLocationRepository
import examen.parkingspotsgent.ui.theme.ParkingspotsGentTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingspotsGentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {
    val container: AppContainer = DefaultAppContainer()
    val parkingSpotLocationsRepository: ParkingSpotLocationRepository = container.parkingSpotLocationsRepository
    val allParkingSpotInfo: List<ParkingSpotInfo>
    runBlocking {
        allParkingSpotInfo = parkingSpotLocationsRepository.getAllParkingSpotInfo()
    }
    LazyColumn {
        items(allParkingSpotInfo){ parkingSpotInfo ->
            Text(
                text = parkingSpotInfo.name,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParkingspotsGentTheme {
        Greeting()
    }
}