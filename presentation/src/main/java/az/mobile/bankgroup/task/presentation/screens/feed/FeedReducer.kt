package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.presentation.base.BaseReducer

class FeedReducer : BaseReducer<FeedState, FeedAction>() {
    override fun reduce(currentState: FeedState, action: FeedAction): FeedState = when (action) {
        is FeedAction.ConnectionSet ->
            currentState.copy(connectionStatus = action.status)
        is FeedAction.StocksUpdated ->
            currentState.copy(
                stocks = action.stocks,
                connectionStatus = if (currentState.isFeedRunning) {
                    FeedConnectionStatus.Connected
                } else {
                    currentState.connectionStatus
                },
            )
        is FeedAction.FeedRunningChanged ->
            currentState.copy(
                isFeedRunning = action.running,
                connectionStatus = if (!action.running) {
                    FeedConnectionStatus.Disconnected
                } else {
                    currentState.connectionStatus
                },
            )
    }
}
