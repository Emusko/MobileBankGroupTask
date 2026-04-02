package az.mobile.bankgroup.task.domain.repository

import az.mobile.bankgroup.task.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockFeedRepository {
    fun observeStocks(): Flow<List<Stock>>

    fun observeFeedRunning(): Flow<Boolean>

    suspend fun startFeed()

    suspend fun stopFeed()
}
