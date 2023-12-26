package examen.parkingspotsgent.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                    viewModel.selectParkingSpot(it)
                    navController.navigate("${ParkingSpotDetailsDestination.route}/${it}")
                },

                    viewModel = viewModel
                    ) }
        composable(route = FilterDestination.route) {
            FilterScreen(
                navigateBack = { navController.popBackStack() },
                onRefresh = { type ->
                    viewModel.setTypeFilter(type)
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        composable(
            route = ParkingSpotDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ParkingSpotDetailsDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) {
            ParkingSpotDetailsScreen(
                navigateBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}