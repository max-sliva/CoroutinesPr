import kotlinx.coroutines.*
import kotlin.random.Random

fun main() = runBlocking {
    print("n = ")
    val n = readlnOrNull()?.toIntOrNull() ?: return@runBlocking

    // 1. Generate array and calculate sequential sum idiomatically
    val mas = IntArray(n) { Random.nextInt(10) }
    println(mas.joinToString(" "))

    val seqSum = mas.sum()
    println("sequence sum = $seqSum")

    print("thrCount = ")
    val thrCount = readlnOrNull()?.toIntOrNull() ?: return@runBlocking

    // FIX: Original Java code ignored remainder elements if n % thrCount != 0.
    // We use ceiling division to ensure all elements are processed.
    val partSize = (n + thrCount - 1) / thrCount

    // Split the array into chunks. This replaces the manual nested loop and array copying.
    val chunks = mas.toList().chunked(partSize)

    println("Waiting for result from coroutine threads...")

    // Launch a background coroutine to print progress every 300ms
    val progressJob = launch {
        while (isActive) {
            println("Calculating...")
            delay(300) // Non-blocking delay
        }
    }

    // 2. Launch concurrent coroutines for each chunk
    val results = coroutineScope {
        chunks.map { chunk ->
            // Dispatchers.Default is optimized for CPU-intensive tasks like summing
            async(Dispatchers.Default) {
                println("${Thread.currentThread().name} arr=${chunk.joinToString()}")
                println("${Thread.currentThread().name} is calculating...")

                delay(2000) // Non-blocking suspension (replaces Thread.sleep)

                chunk.sum() // Calculate and implicitly return the sum
            }
        }.awaitAll() // Suspend until all async blocks finish, then return List<Int>
    }

    // Stop the progress indicator cleanly
    progressJob.cancel()

    // 3. Output results and calculate final sum
    val parallelSum = results.sum()

    results.forEach { println(it) }
    println("parallel sum = $parallelSum")
}