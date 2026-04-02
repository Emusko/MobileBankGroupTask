package az.mobile.bankgroup.task.data.stock.dto

import az.mobile.bankgroup.task.domain.model.Stock
import kotlinx.serialization.Serializable

@Serializable
data class StockWireDto(
    val symbol: String,
    val name: String,
    val description: String,
    val price: Double,
    val lastUpdatedMillis: Long,
) {
    fun toDomain(): Stock = Stock(symbol, name, description, price, lastUpdatedMillis)

    companion object {
        fun fromDomain(stock: Stock): StockWireDto = StockWireDto(
            symbol = stock.symbol,
            name = stock.name,
            description = stock.description,
            price = stock.price,
            lastUpdatedMillis = stock.lastUpdatedMillis,
        )
    }
}
