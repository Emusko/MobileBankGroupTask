package az.mobile.bankgroup.task.data.stock

import az.mobile.bankgroup.task.data.stock.dto.StockWireDto
import az.mobile.bankgroup.task.domain.dispatcher.IoDispatcher
import az.mobile.bankgroup.task.domain.model.Stock
import az.mobile.bankgroup.task.domain.repository.StockFeedRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray

class StockFeedRepositoryImpl @Inject constructor(
    private val client: OkHttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : StockFeedRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)
    private val mutex = Mutex()
    private var feedJob: Job? = null
    private var activeFeedRequests: Int = 0

    private val _stocks = MutableStateFlow(StockCatalog.createInitialStocks())
    private val _feedRunning = MutableStateFlow(false)

    override fun observeStocks(): Flow<List<Stock>> = _stocks.asStateFlow()

    override fun observeFeedRunning(): Flow<Boolean> = _feedRunning.asStateFlow()

    override suspend fun startFeed() {
        mutex.withLock {
            activeFeedRequests += 1
            if (feedJob?.isActive == true) return
            feedJob = scope.launch {
                try {
                    _feedRunning.value = true
                    runFeedLoop()
                } finally {
                    _feedRunning.value = false
                }
            }
        }
    }

    override suspend fun stopFeed() {
        val job = mutex.withLock {
            if (activeFeedRequests > 0) {
                activeFeedRequests -= 1
            }
            if (activeFeedRequests > 0) {
                return@withLock null
            }
            feedJob.also { feedJob = null }
        }
        job?.cancelAndJoin()
    }

    private suspend fun runFeedLoop() {
        while (true) {
            val incoming = Channel<String>(Channel.UNLIMITED)
            var webSocket: WebSocket? = null
            try {
                webSocket = openWebSocket(incoming)
                while (true) {
                    delay(TICK_MS)
                    val updatedStocks = _stocks.value.map { stock ->
                        stock.copy(
                            price = randomizePrice(stock.price),
                            lastUpdatedMillis = System.currentTimeMillis(),
                        )
                    }
                    val payload = updatedStocks.map(StockWireDto::fromDomain)
                    val jsonText = JSONArray(payload.map(StockWireDto::toJsonObject)).toString()
                    val sent = webSocket.send(jsonText)
                    if (!sent) {
                        throw IllegalStateException("WebSocket send() returned false for bulk stock payload")
                    }
                    val echoed = withTimeoutOrNull(ECHO_TIMEOUT_MS) {
                        incoming.receive()
                    } ?: throw IllegalStateException(
                        "Echo timeout (${ECHO_TIMEOUT_MS}ms) for bulk stock payload",
                    )
                    val parsed = try {
                        val array = JSONArray(echoed)
                        val result = mutableListOf<StockWireDto>()
                        repeat(array.length()) { index ->
                            val jsonObject = array.optJSONObject(index) ?: return@repeat
                            result.add(StockWireDto.fromJsonObject(jsonObject))
                        }
                        result
                    } catch (e: Exception) {
                        continue
                    }
                    _stocks.value = parsed.map(StockWireDto::toDomain)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                delay(RECONNECT_DELAY_MS)
            } finally {
                incoming.close()
                webSocket?.close(NORMAL_CLOSE_CODE, "reconnect or stop")
            }
        }
    }

    private suspend fun openWebSocket(incoming: Channel<String>): WebSocket =
        suspendCancellableCoroutine { continuation ->
            val request = Request.Builder().url(WSS_URL).build()
            val listener = object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    continuation.resume(webSocket)
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    incoming.trySend(text)
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    webSocket.close(code, reason)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    incoming.close(t)
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }
            }
            val ws = client.newWebSocket(request, listener)
            continuation.invokeOnCancellation {
                ws.cancel()
            }
        }

    private fun randomizePrice(current: Double): Double {
        val delta = Random.nextDouble(-PRICE_JITTER_FRACTION, PRICE_JITTER_FRACTION)
        return (current * (1.0 + delta)).coerceAtLeast(MIN_PRICE)
    }

    private companion object {
        const val WSS_URL = "wss://ws.postman-echo.com/raw"
        const val TICK_MS = 2_000L
        const val ECHO_TIMEOUT_MS = 15_000L
        const val RECONNECT_DELAY_MS = 1_500L
        const val NORMAL_CLOSE_CODE = 1000
        const val PRICE_JITTER_FRACTION = 0.035
        const val MIN_PRICE = 0.01
    }
}
