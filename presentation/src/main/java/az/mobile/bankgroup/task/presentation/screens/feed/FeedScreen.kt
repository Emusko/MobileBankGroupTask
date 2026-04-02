package az.mobile.bankgroup.task.presentation.screens.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import az.mobile.bankgroup.task.uikit.stock.StockPriceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel,
    onSymbolClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Feed") }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart)
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

                TextButton(
                    onClick = { viewModel.processIntent(FeedIntent.ToggleFeedClicked) },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterEnd),
                ) {
                    Text(if (state.isFeedRunning) "Stop" else "Start")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = state.stocks,
                    key = { it.symbol },
                ) { stock ->
                    StockPriceCard(
                        symbol = stock.symbol,
                        currentPrice = stock.price,
                        indicatorUp = stock.indicator == FeedPriceIndicator.Up,
                        shouldFlash = stock.shouldFlash,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        onClick = {
                            viewModel.processIntent(FeedIntent.SymbolClicked(stock.symbol))
                            onSymbolClick(stock.symbol)
                        },
                    )
                }
            }
        }
    }
}
