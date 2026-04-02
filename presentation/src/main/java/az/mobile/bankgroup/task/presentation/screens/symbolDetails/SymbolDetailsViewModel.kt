package az.mobile.bankgroup.task.presentation.screens.symbolDetails

import androidx.lifecycle.SavedStateHandle
import az.mobile.bankgroup.task.domain.usecase.ObserveStocksUseCase
import az.mobile.bankgroup.task.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class SymbolDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeStocks: ObserveStocksUseCase,
) : BaseViewModel<SymbolDetailsState, SymbolDetailsIntent, SymbolDetailsAction>(
    initialState = SymbolDetailsState(),
    reducer = SymbolDetailsReducer(),
) {
    private val selectedSymbol = savedStateHandle.getStateFlow(SYMBOL_ARG, "")

    init {
        launchInViewModelScope {
            selectedSymbol.collectLatest { symbol ->
                dispatch(SymbolDetailsAction.SymbolChanged(symbol))
            }
        }
        launchInViewModelScope {
            observeStocks().collectLatest { stocks ->
                val symbol = selectedSymbol.value
                val stock = stocks.firstOrNull { it.symbol == symbol }
                dispatch(
                    SymbolDetailsAction.DataLoaded(
                        name = stock?.name.orEmpty(),
                        description = stock?.description.orEmpty(),
                        price = stock?.price ?: 0.0,
                        found = stock != null,
                    ),
                )
            }
        }
    }

    override fun processIntent(intent: SymbolDetailsIntent) = Unit

    companion object {
        const val SYMBOL_ARG = "symbol"
    }
}
