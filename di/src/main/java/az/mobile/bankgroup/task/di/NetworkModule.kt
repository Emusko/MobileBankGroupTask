package az.mobile.bankgroup.task.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheDirectory = File(context.cacheDir, "okhttp_cache")
        val cache = Cache(cacheDirectory, 10L * 1024 * 1024)
        return OkHttpClient.Builder()
            .pingInterval(2, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }
}
