package az.mobile.bankgroup.task.presentation.screens.symbolDetails

data class SymbolDetailsState(
    val symbol: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val found: Boolean = false,
)
