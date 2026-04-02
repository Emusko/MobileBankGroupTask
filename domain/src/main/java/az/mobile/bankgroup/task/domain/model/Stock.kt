package az.mobile.bankgroup.task.domain.model

data class Stock(
    val symbol: String,
    val name: String,
    val description: String,
    val price: Double,
    val lastUpdatedMillis: Long,
)
