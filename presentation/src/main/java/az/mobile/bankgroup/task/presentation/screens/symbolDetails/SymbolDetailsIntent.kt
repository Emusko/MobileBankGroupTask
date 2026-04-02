package az.mobile.bankgroup.task.presentation.screens.symbolDetails

import az.mobile.bankgroup.task.presentation.base.BaseUiIntent

sealed interface SymbolDetailsIntent : BaseUiIntent {
    data object Appeared : SymbolDetailsIntent
}
