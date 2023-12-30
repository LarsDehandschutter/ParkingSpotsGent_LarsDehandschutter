package examen.parkingspotsgent

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import examen.parkingspotsgent.data.ParkingSpotInfo
import examen.parkingspotsgent.data.SpecialParkingSpots
import examen.parkingspotsgent.ui.screens.AppUiState
import examen.parkingspotsgent.ui.screens.FilterBody
import examen.parkingspotsgent.ui.screens.HomeBody
import examen.parkingspotsgent.ui.screens.ParkingSpotDetailsBody
import org.junit.Rule
import org.junit.Test

class ParkingSpotAppScreenTest {
    /**
     * Note: To access to an empty activity, the code uses ComponentActivity instead of
     * MainActivity.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Declare first parkingSpot
     */
    private val parkingSpotOne = ParkingSpotInfo(
        id = "ur_id_1",
        name = "Bourgoyen",
        houseNr = "1",
        type = "Park and Ride",
        streetName = "Bommelstraat",
        capacity = 100,
        infoText = "",
        lon = 0.0,
        lat = 0.0
    )

    /**
     * Declare second parkingSpot
     */
    private val parkingSpotTwo = ParkingSpotInfo(
        id = "ur_id_2",
        name = "Gent-Sint-Pieters",
        houseNr = "2",
        type = "Parking",
        streetName = "VoskensLaan",
        capacity = 1000,
        infoText = "StationsParking",
        lon = 1.0,
        lat = 1.0
    )

    /**
     * Declare list of parkingSpots containing first and second parkingSpot
     */
    private val parkingSpots = listOf(parkingSpotOne, parkingSpotTwo)

    /**
     * Add the type of both parkingSpots in set
     */
    private val types = mutableSetOf(parkingSpotOne.type, parkingSpotTwo.type)


    /**
     * Declare first App Ui State with empty filters and first parkingSpot selected
      */
    private val appUiStateOne = AppUiState(
        typeFilter = mutableSetOf(),
        selectedParkingSpot = parkingSpotOne
    )

    /**
     * Declare second App Ui State with empty filters and "empty" parkingSpot selected
     */
    private val appUiStateTwo = AppUiState(
        typeFilter = mutableSetOf(),
        selectedParkingSpot = SpecialParkingSpots.emptyParkingSpot
    )

    @Test
    fun homeScreen_verifyContent_restrictByType() {
        // When HomeScreen is loaded
        // Allow only the type of first parkingSpot
        // Allow all
        composeTestRule.setContent {
            HomeBody(
                parkingSpotList = parkingSpots,
                typeFilter = mutableSetOf(parkingSpotOne.type),
                onItemClick = { },
                synchronized = true
            )
        }
        // Check first parkingSpot is displayed and can be clicked
        composeTestRule.onNodeWithText(parkingSpotOne.name)
            .assertIsDisplayed()
            .assertHasClickAction()
        // Check second parkingSpot is not in the scrollable list
        composeTestRule.onNodeWithText(parkingSpotTwo.name).assertDoesNotExist()
    }

    @Test
    fun homeScreen_verifyContent_noRestriction() {
        // When HomeScreen is loaded
        composeTestRule.setContent {
            HomeBody(
                parkingSpotList = parkingSpots,
                typeFilter = types,
                onItemClick = { },
                synchronized = true
            )
        }
        // Check whether both parkingSpots are in the scrollable list
        // and are clickable
        composeTestRule.onNodeWithText(parkingSpotTwo.name)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(parkingSpotOne.name)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun homeScreen_verifyContent_TooRestricted() {  //Error path
        // When HomeScreen is loaded
        // Allow all types

        composeTestRule.setContent {
            HomeBody(
                parkingSpotList = parkingSpots,
                typeFilter = mutableSetOf(),
                onItemClick = { },
                synchronized = true
            )
        }
        // Check if the appropriate "special" parkingSpot is in the scrollable list
        // and is clickable
        composeTestRule.onNodeWithText(SpecialParkingSpots.noParkingSpots.name)
            .assertIsDisplayed()
            .assertHasClickAction()
        // Check first parkingSpot is not in the scrollable list
        composeTestRule.onNodeWithText(parkingSpotOne.name).assertDoesNotExist()
        // Check second parkingSpot is not in the scrollable list
        composeTestRule.onNodeWithText(parkingSpotTwo.name).assertDoesNotExist()
    }

    @Test
    fun filterScreen_verifyContent_noRestriction() {
        // When FilterScreen is loaded
        // Allow all types and communes
        composeTestRule.setContent {
            FilterBody(
                onToggleSwitch = { _:MutableSet<String> ->  },
                allTypes = types,
                typeFilter = types
            )
        }
        // Check if all type options are displayed
        // Check whether all corresponding switches are checked
        types.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(it.lowercase()).assertIsDisplayed()
        }
    }

    @Test
    fun filterScreen_verifyContent_restrictByType() {
        // When FilterScreen is loaded
        // Only allow type of first parkingSpot
        // Allow all communes
        composeTestRule.setContent {
            FilterBody(
                onToggleSwitch = {_: MutableSet<String> ->  },
                allTypes = types,
                typeFilter = mutableSetOf(parkingSpotOne.type) ,

            )
        }
        // Check whether all type options are displayed
        types.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
        }
        // Check if switch for type of first parkingSpot is checked
        composeTestRule.onNodeWithContentDescription(parkingSpotOne.type.lowercase()).assertIsDisplayed()
        // Check if switch for type of second parkingSpot is not checked
        composeTestRule.onNodeWithContentDescription(parkingSpotTwo.type.lowercase()).assertDoesNotExist()


    }

    @Test
    fun parkingSpotDetailsScreen_verifyContent_parkingSpotExists() {
        // When ParkingSpotDetailsScreen is loaded
        // and the first parkingSpot is selected
        composeTestRule.setContent {
            ParkingSpotDetailsBody(
                appUiState = appUiStateOne
            )
        }
        // Check if Google maps intent button is displayed, enabled and clickable
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.show_map))
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertHasClickAction()
        // Check if the details of the first parkingSpot are displayed
        // but cannot be clicked
        composeTestRule.onNodeWithText(parkingSpotOne.name)
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    @Test
    fun parkingSpotDetailsScreen_verifyContent_parkingSpotIsEmpty() {   //error path
        // When ParkingSpotDetailsScreen is loaded
        // and the "empty" parkingSpot is selected
        composeTestRule.setContent {
            ParkingSpotDetailsBody(
                appUiState = appUiStateTwo
            )
        }
        // Check if Google maps intent button is displayed
        // but not enabled
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.show_map))
            .assertIsDisplayed()
            .assertIsNotEnabled()
        // Check if empty parkingSpot details are displayed
        // and cannot be clicked
        composeTestRule.onNodeWithText(
            text = composeTestRule.activity.getString(R.string.parkingSpot),
            substring = true
        )
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }
}