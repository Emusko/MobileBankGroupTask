package az.mobile.bankgroup.task.di

import az.mobile.bankgroup.task.data.stock.StockFeedRepositoryImpl
import az.mobile.bankgroup.task.domain.repository.StockFeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StockFeedRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockFeedRepository(impl: StockFeedRepositoryImpl): StockFeedRepository
}
