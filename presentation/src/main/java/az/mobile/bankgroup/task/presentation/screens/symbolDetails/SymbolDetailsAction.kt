package az.mobile.bankgroup.task.presentation.screens.symbolDetails

import az.mobile.bankgroup.task.presentation.base.BaseAction

sealed interface SymbolDetailsAction : BaseAction {
    data class SymbolChanged(val symbol: String) : SymbolDetailsAction
    data class DataLoaded(
        val name: String,
        val description: String,
        val price: Double,
        val found: Boolean,
    ) : SymbolDetailsAction
}
