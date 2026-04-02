package az.mobile.bankgroup.task.presentation.screens.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Feed") },
                navigationIcon = {
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = if (state.connectionStatus == FeedConnectionStatus.Connected) {
                                "🟢"
                            } else {
                                "🔴"
                            },
                        )
                        Text(
                            text = if (state.connectionStatus == FeedConnectionStatus.Connected) {
                                "Connected"
                            } else {
                                "Disconnected"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.processIntent(FeedIntent.ToggleFeedClicked) },
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text(if (state.isFeedRunning) "Stop" else "Start")
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "${state.stocks.size} symbols",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
