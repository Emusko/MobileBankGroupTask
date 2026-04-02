package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.domain.model.Stock
import az.mobile.bankgroup.task.presentation.base.BaseAction

sealed interface FeedAction : BaseAction {
    data class ConnectionSet(val status: FeedConnectionStatus) : FeedAction
    data class StocksUpdated(val stocks: List<Stock>) : FeedAction
    data class FeedRunningChanged(val running: Boolean) : FeedAction
}
