package az.mobile.bankgroup.task.presentation.screens.symbolDetails

import az.mobile.bankgroup.task.presentation.base.BaseReducer

class SymbolDetailsReducer : BaseReducer<SymbolDetailsState, SymbolDetailsAction>() {
    override fun reduce(
        currentState: SymbolDetailsState,
        action: SymbolDetailsAction,
    ): SymbolDetailsState = when (action) {
        is SymbolDetailsAction.SymbolChanged -> currentState.copy(symbol = action.symbol)
        is SymbolDetailsAction.DataLoaded -> currentState.copy(
            name = action.name,
            description = action.description,
            price = action.price,
            found = action.found,
        )
    }
}
