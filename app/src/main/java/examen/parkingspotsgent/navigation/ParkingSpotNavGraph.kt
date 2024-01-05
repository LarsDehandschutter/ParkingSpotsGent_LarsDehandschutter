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
/**
 * Provides Navigation graph for the application.
 * The application has a home, filter and parkingSpot details screen
 * A bidirectional transition between home and filter screen is possible,
 * as well as a bidirectional transition between home and parkingSpot details screen.
 * Click sounds are generated in the click callbacks provided
 * @property navController the nav host controller.
 * @property viewModel the parkingSpot view model.
 * @constructor creates the parkingSpot nav host.
 */
@Composable
fun ParkingSpotNavHost(
    navController: NavHostController,
    viewModel: ParkingSpotsViewModel,
    modifier: Modifier = Modifier
){
    val view = LocalView.current
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(
                /**
                 * Called when the Edit type floating action button is clicked
                 */
                navigateToFilter = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    navController.navigate(FilterDestination.route) },
                /**
                 * Called when a parkingSpot in the scrolling list is clicked
                 * The selected parkingSpot state is changed
                 * When the primary key of a "special" parkingSpot comes in,
                 * the selected parkingSpot state is cleared to the "empty" parkingSpot
                 */
                navigateToDetails = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    if (it != SpecialParkingSpots.noParkingSpots.id)

                        viewModel.selectParkingSpot(it)
                    else
                        viewModel.clearParkingSpot()
                    navController.navigate(ParkingSpotDetailsDestination.route)
                },

                    viewModel = viewModel
                    ) }
        composable(route = FilterDestination.route) {
            /**
             * Called when the back button in the App bar is clicked
             */
            FilterScreen(
                navigateBack = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    navController.popBackStack() },
                /**
                 * Called when a switch is toggled, the type state set is updated
                 */
                onToggleSwitch = { type ->
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    viewModel.setTypeFilter(type)
                },
                viewModel = viewModel
            )
        }
        composable(
            route = ParkingSpotDetailsDestination.route) {
            /**
             * Called when the back button in the App bar is clicked
             */
            ParkingSpotDetailsScreen(
                navigateBack = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}