package examen.parkingspotsgent

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import examen.parkingspotsgent.components.assertCurrentRouteName
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.ui.screens.FilterDestination
import examen.parkingspotsgent.ui.screens.HomeDestination
import examen.parkingspotsgent.ui.screens.ParkingSpotDetailsDestination
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ParkingSpotScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupParkingSpotNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ParkingSpotApp(navController = navController)
        }
    }
    @Test
    fun parkingSpotNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(HomeDestination.route)
    }
    @Test
    fun parkingSpotNavHost_verifyBackNavigationNotShownOnHomeScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }
    @Test
    fun parkingSpotNavHost_clickBuild_navigatesToFilterScreen() {
        val buildText = composeTestRule.activity.getString(R.string.filter_title)
        // Click on FAB
        composeTestRule.onNodeWithContentDescription(buildText)
            .performClick()
        navController.assertCurrentRouteName(FilterDestination.route)
    }
    @Test
    fun parkingSpotNavHost_clickBackOnFilterScreen_navigatesToHomeScreen() {
        navigateToFilterScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(HomeDestination.route)
    }
    @Test
    fun parkingSpotNavHost_clickParkingSpotOnHomeScreen_navigatesToParkingSpotDetailsScreen() {

        val testTag = composeTestRule.activity.getString(R.string.testItems)
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithTag(testTag = testTag)
                .fetchSemanticsNodes().isNotEmpty()

        }
            // Click on first parkingSpot in scrollable list
        composeTestRule.onAllNodesWithTag(testTag = testTag)
            .onFirst()
            .performClick()
        navController.assertCurrentRouteName(ParkingSpotDetailsDestination.route)
    }

    @Test
    fun parkingSpotNavHost_clickBackOnParkingSpotDetailsScreen_navigatesToHomeScreen() {
        navigateToParkingSpotDetailsScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(HomeDestination.route)
    }
    @Test
    fun parkingSpotNavHost_clickClearOnFilterScreen_clickBackOnFilterScreen_navigatesToHomeScreen() {
        val testTag = composeTestRule.activity.getString(R.string.sync)
        // Wait until the FAB is recomposed with the specified test tag
        // A timeout of 15 seconds is applied
        composeTestRule.waitUntil(timeoutMillis = 15000)  {
            composeTestRule.onAllNodesWithTag(testTag = testTag)
                .fetchSemanticsNodes().isNotEmpty()

        }
        navigateToFilterScreen()

        composeTestRule.onAllNodesWithTag(R.string.switchButton.toString())
            .onFirst()
            .performClick()
        composeTestRule.onAllNodesWithTag(R.string.switchButton.toString())
            .onLast()
            .performClick()
        performNavigateUp()
        navController.assertCurrentRouteName(HomeDestination.route)
        // Check if the "special" doctor is displayed
        composeTestRule.onNodeWithText(SpecialParkingSpots.noParkingSpots.name)   //Error path
            .assertIsDisplayed()
    }


    /**
     * Click FAB on Home screen
     */
    private fun navigateToFilterScreen() {
        val buildText = composeTestRule.activity.getString(R.string.filter_title)
        composeTestRule.onNodeWithContentDescription(buildText)
            .performClick()
    }

    /**
     * Click first parkingSpot in scrollable list
     */
    private fun navigateToParkingSpotDetailsScreen() {
        val testTag = composeTestRule.activity.getString(R.string.testItems)
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithTag(testTag = testTag)
                .fetchSemanticsNodes().isNotEmpty()

        }
        // Click on first parkingSpot in scrollable list
        composeTestRule.onAllNodesWithTag(testTag = testTag)
            .onFirst()
            .performClick()
    }

    /**
     * Click back in app Top Bar
     */
    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
}
