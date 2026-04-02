package az.mobile.bankgroup.task.domain.usecase

import az.mobile.bankgroup.task.domain.repository.StockFeedRepository
import javax.inject.Inject

class StopStockFeedUseCase @Inject constructor(
    private val repository: StockFeedRepository,
) {
    suspend operator fun invoke() {
        repository.stopFeed()
    }
}
