import java.awt.BorderLayout
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class MainGUI_Lab2: JFrame {
    val tArea = JTextArea()
    constructor(
        title: String,
        myTasks: Array<PausableTask>,
    ){
        super.setTitle(title)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        add(setToolBar(myTasks), BorderLayout.NORTH)
        tArea.setCaretPosition(tArea.document.length)
        add(JScrollPane(tArea), BorderLayout.CENTER)
//        setSize(500, 300) //размер окна
        pack()
        minimumSize = size
        isVisible = true //делаем окно видимым
    }

    private fun setToolBar(tasks: Array<PausableTask>): Box {
        val boxArray = arrayOf<Box>()
        val bar = Box(BoxLayout.Y_AXIS)
        var i = -1
        for (curTask in tasks) {
            i++
            curTask.onTick= {
                tArea.append("${curTask.name} count = ${curTask.counter}\n")
                tArea.setCaretPosition(tArea.document.length);
            }
            val toolBar = Box(BoxLayout.X_AXIS)
            val startCoroutine = JButton("Start$i ${curTask.name}")
            val resumeCoroutine = JButton("Resume$i ${curTask.name}")
            val pauseCoroutine = JButton("Pause$i ${curTask.name}")//кнопка для приостановки потока
            resumeCoroutine.isEnabled = false
            val stopCoroutine = JButton("Stop$i ${curTask.name}")
            stopCoroutine.isEnabled = false

            startCoroutine.addActionListener {
                curTask.start()
                println("Coroutine${curTask.name} started")
                stopCoroutine.isEnabled = true
                startCoroutine.isEnabled = false
            }

            stopCoroutine.addActionListener {
                curTask.stop()
                curTask.counter = 0
                stopCoroutine.isEnabled = false
                startCoroutine.isEnabled = true
            }

            pauseCoroutine.addActionListener {
                curTask.pause()
                pauseCoroutine.isEnabled = false
                resumeCoroutine.isEnabled = true
            }

            resumeCoroutine.addActionListener {
                curTask.resume()
                resumeCoroutine.isEnabled = false
                pauseCoroutine.isEnabled = true
            }
            toolBar.add(Box.createHorizontalStrut(20)) // распорка
            toolBar.add(startCoroutine) //вставляем кнопку в контейнер
            toolBar.add(Box.createHorizontalGlue()) //пружина
            toolBar.add(pauseCoroutine) //вставляем кнопку в контейнер
            toolBar.add(Box.createHorizontalGlue())//пружина
            toolBar.add(resumeCoroutine) //вставляем кнопку в контейнер
            toolBar.add(Box.createHorizontalGlue())//пружина
            toolBar.add(stopCoroutine) //вставляем кнопку в контейнер
            toolBar.add(Box.createHorizontalStrut(20)) //распорка
            bar.add(toolBar)
        }
        return bar //возвращаем контейнер с кнопками
    }


}