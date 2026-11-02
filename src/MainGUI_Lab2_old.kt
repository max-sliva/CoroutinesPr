import java.awt.BorderLayout
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class MainGUI_Lab2_old: JFrame {
//    val corNames = arrayOf<String>()
lateinit var myTasks: Array<PausableTask>
    val tArea = JTextArea()
    constructor(
        title: String,
        corNames: Array<String>,
        myTask: Array<PausableTask>,
    ){
        super.setTitle(title)
        this.myTasks = myTask
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 300) //размер окна
        minimumSize = size
        setLocationRelativeTo(null)
        add(setToolBar(corNames), BorderLayout.NORTH)

        add(JScrollPane(tArea), BorderLayout.CENTER) //todo сделать прокрутку до текущего значения
        isVisible = true //делаем окно видимым
    }

    private fun setToolBar(corNames:  Array<String>): Box {
        val boxArray = arrayOf<Box>()
        val bar = Box(BoxLayout.Y_AXIS)
        var i = -1
        for (name in corNames) {
            i++
            val curTask = myTasks[i]
            curTask.onTick= {
                tArea.append("${curTask.name} count = ${curTask.counter}\n")
            }
            val toolBar = Box(BoxLayout.X_AXIS)
            val startCoroutine = JButton("Start$i $name")
            val resumeCoroutine = JButton("Resume$i $name")
            val pauseCoroutine = JButton("Pause$i $name")//кнопка для приостановки потока
            resumeCoroutine.isEnabled = false
            val stopCoroutine = JButton("Stop$i $name")
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