package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.domain.model.Stock
import az.mobile.bankgroup.task.domain.usecase.ObserveStockFeedRunningUseCase
import az.mobile.bankgroup.task.domain.usecase.ObserveStocksUseCase
import az.mobile.bankgroup.task.domain.usecase.StartStockFeedUseCase
import az.mobile.bankgroup.task.domain.usecase.StopStockFeedUseCase
import az.mobile.bankgroup.task.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val observeStocks: ObserveStocksUseCase,
    private val observeFeedRunning: ObserveStockFeedRunningUseCase,
    private val startStockFeed: StartStockFeedUseCase,
    private val stopStockFeed: StopStockFeedUseCase,
) : BaseViewModel<FeedState, FeedIntent, FeedAction>(
    initialState = FeedState(),
    reducer = FeedReducer(),
) {
    private var previousPrices: Map<String, Double> = emptyMap()

    init {
        launchInViewModelScope {
            observeStocks().collect { stocks ->
                dispatch(FeedAction.StocksUpdated(stocks.toUiItems()))
            }
        }
        launchInViewModelScope {
            observeFeedRunning().collect { running ->
                dispatch(FeedAction.FeedRunningChanged(running))
            }
        }
    }

    override fun processIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.ToggleFeedClicked -> {
                launchInViewModelScope {
                    if (state.value.isFeedRunning) {
                        stopStockFeed()
                    } else {
                        startStockFeed()
                        dispatch(FeedAction.ConnectionSet(FeedConnectionStatus.Connected))
                    }
                }
            }
            is FeedIntent.SymbolClicked -> Unit
        }
    }

    private fun List<Stock>.toUiItems(): List<FeedStockItem> {
        val currentPriceMap = associate { it.symbol to it.price }
        val mapped = map { stock ->
            val previous = previousPrices[stock.symbol]
            val indicator = if (previous == null || stock.price >= previous) {
                FeedPriceIndicator.Up
            } else {
                FeedPriceIndicator.Down
            }
            val shouldFlash = previous != null && stock.price != previous
            FeedStockItem(
                symbol = stock.symbol,
                name = stock.name,
                description = stock.description,
                price = stock.price,
                indicator = indicator,
                shouldFlash = shouldFlash,
            )
        }.sortedByDescending { it.price }
        previousPrices = currentPriceMap
        return mapped
    }
}
