package az.mobile.bankgroup.task.domain.usecase

import az.mobile.bankgroup.task.domain.repository.StockFeedRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveStockFeedRunningUseCase @Inject constructor(
    private val repository: StockFeedRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.observeFeedRunning()
}
