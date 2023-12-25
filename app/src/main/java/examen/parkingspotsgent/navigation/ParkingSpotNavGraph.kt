package examen.parkingspotsgent.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import examen.parkingspotsgent.ui.screens.HomeDestination
import examen.parkingspotsgent.ui.screens.HomeScreen
import examen.parkingspotsgent.ui.screens.ParkingSpotsViewModel

@Composable
fun ParkingSpotNavHost(
    navController: NavHostController,
    viewModel: ParkingSpotsViewModel,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(

                viewModel = viewModel
            )
        }
    }
}