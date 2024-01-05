package examen.parkingspotsgent.ui.screens

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import examen.parkingspotsgent.ParkingSpotTopAppBar
import examen.parkingspotsgent.R
import examen.parkingspotsgent.navigation.NavigationDestination
import examen.parkingspotsgent.ui.theme.ParkingspotsGentTheme

object FilterDestination : NavigationDestination {
    override val route = "filter"
    override val titleRes = R.string.filter_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    navigateBack: () -> Unit,
    onToggleSwitch: (MutableSet<String>) -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: ParkingSpotsViewModel
) {
    val appUiState by viewModel.appUiState.collectAsState()
    val typeFilter = appUiState.typeFilter.toMutableSet() //make copy
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ParkingSpotTopAppBar(
                title = stringResource(FilterDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        FilterBody(
            onToggleSwitch = onToggleSwitch,
            allTypes = viewModel.types,
            typeFilter = typeFilter,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}
@VisibleForTesting
@Composable
internal fun FilterBody(
    onToggleSwitch: (MutableSet<String>) -> Unit,
    allTypes: MutableSet<String>,
    typeFilter: MutableSet<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        OptionList(
            onToggleSwitch = onToggleSwitch,
            allTypes = allTypes,
            typeFilter = typeFilter,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}
@Composable
private fun OptionList(
    onToggleSwitch: (MutableSet<String>) -> Unit,
    allTypes: MutableSet<String>,
    typeFilter: MutableSet<String>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        /**
         * Display the type switches
         */
        items(items = allTypes.toList(), key = { it }) {
            FilterOption(
                onToggleSwitch  = onToggleSwitch,
                option = it,
                typeFilter = typeFilter,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}
@Composable
private fun FilterOption(
    onToggleSwitch: (MutableSet<String>) -> Unit,
    option: String,
    typeFilter: MutableSet<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        FilterOptionRow(
            onToggleSwitch = onToggleSwitch,
            option = option,
            typeFilter = typeFilter
        )
    }
}
@Composable
private fun FilterOptionRow(
    onToggleSwitch: (MutableSet<String>) -> Unit,
    option: String,
    typeFilter: MutableSet<String>,
    modifier: Modifier = Modifier
) {
    val checked = typeFilter.contains(option)
    Row(
        modifier = modifier
            .padding(start = dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = option,
            style = MaterialTheme.typography.titleMedium,
            /**
             * Mark the type option bold in the Ui
             */
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
           checked = checked,
           modifier =Modifier.testTag(stringResource(R.string.switchButton)),
            /**
             * Called when the switch is toggled
             * and call the callback with type filter set
             */
            onCheckedChange = {
                if (it){
                    typeFilter.add(option)
                }else {
                    typeFilter.remove(option)
                }
                onToggleSwitch(
                    typeFilter
                )
            },
            /**
             * In case the switch is on, put an additional check icon
             */
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = option,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}




@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    ParkingspotsGentTheme {
        val allTypes: MutableSet<String> = mutableSetOf("Park and Ride", "Parking")
        FilterBody(
            onToggleSwitch = { _:MutableSet<String> ->  },
            allTypes = allTypes,
            typeFilter = mutableSetOf()
        )
    }
}