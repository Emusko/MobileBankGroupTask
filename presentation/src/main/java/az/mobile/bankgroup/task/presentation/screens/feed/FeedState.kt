package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.domain.model.Stock

data class FeedState(
    val connectionStatus: FeedConnectionStatus = FeedConnectionStatus.Disconnected,
    val stocks: List<Stock> = emptyList(),
    val isFeedRunning: Boolean = false,
)
