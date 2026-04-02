package az.mobile.bankgroup.task.data.stock.dto

import az.mobile.bankgroup.task.domain.model.Stock
import org.json.JSONObject

data class StockWireDto(
    val symbol: String,
    val name: String,
    val description: String,
    val price: Double,
    val lastUpdatedMillis: Long,
) {
    fun toDomain(): Stock = Stock(symbol, name, description, price, lastUpdatedMillis)

    fun toJsonObject(): JSONObject = JSONObject().apply {
        put("symbol", symbol)
        put("name", name)
        put("description", description)
        put("price", price)
        put("lastUpdatedMillis", lastUpdatedMillis)
    }

    companion object {
        fun fromDomain(stock: Stock): StockWireDto = StockWireDto(
            symbol = stock.symbol,
            name = stock.name,
            description = stock.description,
            price = stock.price,
            lastUpdatedMillis = stock.lastUpdatedMillis,
        )

        fun fromJsonObject(json: JSONObject): StockWireDto = StockWireDto(
            symbol = json.optString("symbol"),
            name = json.optString("name"),
            description = json.optString("description"),
            price = json.optDouble("price"),
            lastUpdatedMillis = json.optLong("lastUpdatedMillis"),
        )
    }
}
