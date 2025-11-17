package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.LeaderCategory
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.User
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.view.TimeMenu
import com.bachnn.feature.viewpager.viewmodel.InfoUiState
import com.bachnn.feature.viewpager.viewmodel.InfoViewModel
import com.bachnn.feature.viewpager.viewmodel.SettingsUiState
import com.bachnn.feature.viewpager.viewmodel.SettingsViewModel


@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateHome: (Int, Any) -> Unit
) {
    InfoPage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            ),
        viewModel = viewModel,
        settingsViewModel = settingsViewModel,
        navigateHome
    )
}

@Composable
fun InfoPage(
    modifier: Modifier,
    viewModel: InfoViewModel,
    settingsViewModel: SettingsViewModel,
    navigateHome: (Int, Any) -> Unit
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())

        ) {
            MilestonesView(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            TitleView(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
            )
            Spacer(Modifier.height(16.dp))

            LeaderView(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text("User", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.weight(1f))
                    Text("Place", style = MaterialTheme.typography.titleMedium)
                }
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                when (viewModel.infoUiState) {
                    is InfoUiState.Success -> {
                        val photographer =
                            (viewModel.infoUiState as InfoUiState.Success).photographer
                        Column {

                            photographer.forEachIndexed { index, photographer ->
                                LeaderItem(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            navigateHome(13, photographer.id)
                                        },
                                    photographer,
                                    index.toString()
                                )
                            }
                        }
                    }

                    is InfoUiState.Loading -> {
                        PagerLoading()
                    }

                    is InfoUiState.Error -> {

                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("...", style = MaterialTheme.typography.titleLarge)
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                when (settingsViewModel.settingsUiState) {
                    is SettingsUiState.Success -> {
                        val user =
                            (settingsViewModel.settingsUiState as SettingsUiState.Success).user
                        LeaderItem(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            user,
                            "78854"
                        )
                    }

                    is SettingsUiState.Loading -> {
                        PagerLoading()
                    }

                    is SettingsUiState.Error -> {

                    }
                }
            }
        }
    }
}


@Composable
fun MilestonesView(modifier: Modifier) {

    Text(
        stringResource(R.string.milestonse),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    )


    // row
    Row(modifier.horizontalScroll(rememberScrollState())) {
        MilestonesItem(
            modifier = Modifier
                .width(180.dp)
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                ), title = "Milestones unlocked", number = "0/42"
        )

        MilestonesItem(
            modifier = Modifier
                .width(180.dp)
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                ), title = "Total Views", number = "0"
        )

        MilestonesItem(
            modifier = Modifier
                .width(180.dp)
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                ), title = "Next Milestone", number = "5k"
        )
    }

    NumberView("50k")
    NumberView("25k")
    NumberView("20k")
    NumberView("10k")
    NumberView("5k")

}

@Composable
fun MilestonesItem(modifier: Modifier, title: String, number: String) {
    Box(modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                number,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun NumberView(title: String) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(300.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(8.dp)
        )
    }
}


@Composable
fun TitleView(modifier: Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(6.dp)
                .align(alignment = Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "",
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .padding(6.dp)
            )

            Text(
                stringResource(R.string.title_fire),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}


@Composable
fun LeaderView(modifier: Modifier) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.leader_broad),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(Modifier.weight(1f))

            val list = ArrayList<LeaderCategory>()
            list.add(LeaderCategory("All time", true))
            list.add(LeaderCategory("Last 30 Days", false))

            TimeMenu(list)
        }
    }
}

@Composable
fun LeaderItem(modifier: Modifier, photographer: Photographer, number: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleNetworkImage(modifier = Modifier.size(56.dp), photographer.url)

        Spacer(Modifier.width(8.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                photographer.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Text(number, style = MaterialTheme.typography.titleMedium)

    }
}


@Composable
fun LeaderItem(modifier: Modifier, photographer: User, number: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        CircleNetworkImage(modifier = Modifier.size(56.dp), photographer.photoUrl)

        Spacer(Modifier.width(8.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                photographer.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Text(number, style = MaterialTheme.typography.titleMedium)

    }
}
