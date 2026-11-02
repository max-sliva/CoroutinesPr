import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*


class GUI_Lab3: JFrame {
    constructor(
        title: String,
        myTasks: Array<PausableTask2>,
    ) {
        super.setTitle(title)
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 500)
        val centralBox = Box(BoxLayout.X_AXIS)

        val changeButton = JButton("exChange") //объекты для обмена значениями
        changeButton.addActionListener {   //слушатель нажатия кнопки
            val dx: Int = myTasks[0].dx //меняем значения dx в потоках
            myTasks[0].dx = myTasks[1].dx
            myTasks[1].dx = dx
        }
        myTasks.forEach {
            centralBox.add(createBox(it.name, it))
            if (it == myTasks.first() ) centralBox.add(changeButton)
        }
        add(centralBox, BorderLayout.CENTER)
        pack()
        minimumSize = size
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createBox(text: String?, task: PausableTask2): Box {
        val tempBox = Box(BoxLayout.Y_AXIS) //главная коробка области
        //кнопки старта и стопа с соответствующими названиями
        val startButton = JButton("start$text")
        val stopButton = JButton("stop$text")
        val textArea = JTextArea() //текстовая область
        startButton.setAlignmentX(CENTER_ALIGNMENT) //выравниваем по центру все элементы
        stopButton.setAlignmentX(CENTER_ALIGNMENT)
        textArea.setAlignmentX(CENTER_ALIGNMENT)
        task.onTick= {
            textArea.append("${task.name} count = ${task.counter}\n")
            textArea.setCaretPosition(textArea.document.length);
        }
        startButton.addActionListener {   //слушатель для кнопки старта
            task.start()
        }
        stopButton.addActionListener{  //слушатель для кнопки стопа
            task.stop()
        }
        tempBox.add(startButton) //добавляем все объекты в коробку
        tempBox.add(stopButton)
        tempBox.add(JScrollPane(textArea))
        return tempBox //возвращаем коробку
    }


}