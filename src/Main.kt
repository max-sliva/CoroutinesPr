import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

var stop = false

val funcArray = arrayOf(::printTime, ::printTime)

suspend fun main() = coroutineScope {
    val corNames = arrayOf("CoroutineA", "CoroutineB")
    val myTask1 = PausableTask3(name = "Task 0", scope = GlobalScope,
//        onTick = { counter ->
//        println("Task 1 Tick #$counter")
//    }
    )
    val myTask2 = PausableTask3(name = "Task 1",scope = GlobalScope, dx = 2
//        onTick = { counter ->
//        println("Task 2 Tick #$counter")
//    }
    )
//myTask1.start()
    val tasksArray = arrayOf(myTask1, myTask2)
//    val myFrame = MainGUI_Lab2("MyFrame", corNames, tasksArray)
//    val myFrame = MainGUI_Lab2("MyFrame",  tasksArray)
//    val myFrame = GUI_Lab3("MyFrame",  tasksArray)
    val myFrame = GUI_Lab4("MyFrame",  tasksArray)
//    val timeSource = TimeSource.Monotonic
//    var mark1 = timeSource.markNow()
//    var i = 1
//    for (myF in funcArray) {
//        launch {
//            myF((500*i++).toLong())
//        }
//    }
//    var mark2 = timeSource.markNow()
//    while ((mark2 - mark1).inWholeSeconds < 2) {
//        mark2 = timeSource.markNow()
//    }
//    stop = true
//    delay(1000)
//    println("main ended")
}

suspend fun showUserInfo() {
    println("main")
    val numberFlow = flowFun()
    numberFlow.collect { n -> println(n) }
    println("main ended")
}

suspend fun flowFun(): Flow<String> = flow {
    println("Flow started")
    for (item in 1..5) {
        emit((item * item).toString())
        delay(1000)
    }
}

suspend fun printTime(time: Long) {
    while (!stop) {
        delay(time)
        println("$time ms")
    }
}