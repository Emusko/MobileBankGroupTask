package az.mobile.bankgroup.task.domain.usecase

import az.mobile.bankgroup.task.domain.model.Stock
import az.mobile.bankgroup.task.domain.repository.StockFeedRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveStocksUseCase @Inject constructor(
    private val repository: StockFeedRepository,
) {
    operator fun invoke(): Flow<List<Stock>> = repository.observeStocks()
}
