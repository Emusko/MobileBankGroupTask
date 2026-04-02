package az.mobile.bankgroup.task.presentation.screens.feed

data class FeedStockItem(
    val symbol: String,
    val name: String,
    val description: String,
    val price: Double,
    val indicator: FeedPriceIndicator,
    val shouldFlash: Boolean,
)
