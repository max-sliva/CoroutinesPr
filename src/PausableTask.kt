import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PausableTask(
    val name: String,
    private val scope: CoroutineScope,
    private val intervalMs: Long = 1000L,
    //  onTick: suspend (Int) -> Unit
) {
    @Volatile
    var onTick: suspend (Int) -> Unit = { println(name + " " +counter++) }

    var counter = 0
//    fun setOnTick(block: suspend (Int) -> Unit) {
//        onTick = block
//    }

    private var job: Job? = null
    private val isPaused = MutableStateFlow(false)

    val isRunning: Boolean get() = job?.isActive == true

    fun start() {
        if (isRunning) return
        println("Coroutine started in job")
        job = scope.launch {

            // println(counter)
            while (isActive) {
                waitWhilePaused()
                if (!isActive) break
                onTick(counter++)
//                println(counter++)
                delay(intervalMs)
            }
        }
    }
    fun pause() { isPaused.value = true }
    fun resume() { isPaused.value = false }

    fun stop() {
//        job?.cancelAndJoin()
        job?.cancel()
        job = null
        isPaused.value = false
    }

    private suspend fun waitWhilePaused() {
        isPaused.first { !it }
    }
}