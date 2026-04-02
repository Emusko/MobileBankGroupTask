package az.mobile.bankgroup.task.presentation.screens.feed

data class FeedState(
    val connectionStatus: FeedConnectionStatus = FeedConnectionStatus.Disconnected,
    val stocks: List<FeedStockItem> = emptyList(),
    val isFeedRunning: Boolean = false,
)
