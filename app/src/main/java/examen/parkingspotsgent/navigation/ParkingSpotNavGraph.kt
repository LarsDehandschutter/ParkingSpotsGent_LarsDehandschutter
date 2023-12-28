package examen.parkingspotsgent.navigation

import android.view.SoundEffectConstants
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.ui.screens.FilterDestination
import examen.parkingspotsgent.ui.screens.FilterScreen
import examen.parkingspotsgent.ui.screens.HomeDestination
import examen.parkingspotsgent.ui.screens.HomeScreen
import examen.parkingspotsgent.ui.screens.ParkingSpotDetailsDestination
import examen.parkingspotsgent.ui.screens.ParkingSpotDetailsScreen
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
                navigateToFilter = { navController.navigate(FilterDestination.route) },
                navigateToDetails = {
                    if (it != SpecialParkingSpots.noParkingSpots.id)
                        viewModel.selectParkingSpot(it)
                    else
                        viewModel.clearParkingSpot()
                    navController.navigate(ParkingSpotDetailsDestination.route)
                },

                    viewModel = viewModel
                    ) }
        composable(route = FilterDestination.route) {
            FilterScreen(
                navigateBack = { navController.popBackStack() },
                onToggleSwitch = { type ->
                    viewModel.setTypeFilter(type)
                },
                viewModel = viewModel
            )
        }
        composable(
            route = ParkingSpotDetailsDestination.route) {
            ParkingSpotDetailsScreen(
                navigateBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}