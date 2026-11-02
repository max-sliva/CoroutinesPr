import kotlinx.coroutines.*
import java.util.Random
import java.util.Scanner

suspend fun sumPart(part: IntArray): Int {
    println("${Thread.currentThread().name} arr=${part.contentToString()}")
    println("${Thread.currentThread().name} is calculating...")
    delay(2000) // имитация долгой работы
    return part.sum()
}

fun main() = runBlocking {
    print("n = ")
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()

    val random = Random()
    val mas = IntArray(n) { random.nextInt(10) }
    println(mas.joinToString(" ") { " $it" })  // hmm
    val seqSum = mas.sum()
    println()
    println("sequense sum = $seqSum")

    print("thrCount = ")
    val thrCount = scanner.nextInt()

    val partArraySize = n / thrCount

    // split: preserving original behavior (remainder ignored)
    val parts = List(thrCount) { i ->
        IntArray(partArraySize) { j -> mas[j + partArraySize * i] }
    }

    val results = parts.map { part ->
        async(Dispatchers.Default) { sumPart(part) }
    }

    println("Waiting for result from callable threads...")
    while (!results[0].isCompleted) {
        println("Calculating...")
        delay(300)
    }

    var sum = 0
    for (r in results) {
        val v = r.await()
        println(v)
        sum += v
    }
    println("parallel sum = $sum")

    scanner.close()
}