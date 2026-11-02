import java.awt.BorderLayout
import java.awt.EventQueue
import java.awt.event.ActionEvent
import javax.swing.*


class GUI_Lab4: JFrame {
    lateinit var myList: JList<Int>
    constructor(title: String, myTasks: Array<PausableTask3>): super(title) {
        defaultCloseOperation  = JFrame.EXIT_ON_CLOSE
        setSize(500, 500)
        val centralBox = Box(BoxLayout.X_AXIS)
        val uppperBox: Box = createUpperBox()
        myTasks.forEach {
            centralBox.add(createBox(it.name, it))
            if (it == myTasks.first() ) centralBox.add(Box.createHorizontalStrut(50))
        }
        val butDown = JButton("startALL")
        butDown.addActionListener {
            myTasks.forEach {
                it.start()
            }
        }
        add(uppperBox, BorderLayout.NORTH)
        add(centralBox, BorderLayout.CENTER)
        add(butDown, BorderLayout.SOUTH)
        pack()
        minimumSize = size
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createUpperBox(): Box { //функция для создания верхней коробки с кнопками +, - и набором чисел
        val tempBox = Box(BoxLayout.X_AXIS) //сама коробка
        myList = JList<Int>() //список с числами
        myList.setLayoutOrientation(JList.HORIZONTAL_WRAP) //для горизонтального отображения списка
        myList.setVisibleRowCount(1)//в один ряд

        val myModel = DefaultListModel<Int?>() //модель данных для списка
        myList.setModel(myModel) //связываем список с моделью
        for (i in 0..9)  //в цикле заполняем модель данными
            myModel.addElement(i)
        val butPlus = JButton("+") //кнопка для увеличения списка
        butPlus.addActionListener{  //добавляем новый элемент, больше последнего на 1
            myModel.addElement(myModel.lastElement()!! + 1)
        }
        val butMinus = JButton("-") //кнопка для уменьшения списка
        butMinus.addActionListener{ e: ActionEvent? ->  //удаляем последний элемент
            myModel.remove(myModel.size - 1)
        }
        tempBox.add(butMinus) //добавляем всё в коробку
        tempBox.add(Box.createHorizontalGlue()) //между объектами вставляем пружины
        tempBox.add(myList)
        tempBox.add(Box.createHorizontalGlue())
        tempBox.add(butPlus)
        return tempBox //возвращаем коробку
    }

    private fun createBox(text: String?, thread: PausableTask3): Box { //функция для коробки каждого потока
        val tempBox = Box(BoxLayout.Y_AXIS) //сама коробка
        tempBox.setAlignmentX(CENTER_ALIGNMENT) //выравнивание в коробке
        val startButton = JButton("start$text") //кнопки старта и стопа
        val stopButton = JButton("stop$text")
        val textArea = JTextArea() //текстовое поле
        startButton.setAlignmentX(CENTER_ALIGNMENT) //выравнивание каждого элемента в коробке
        stopButton.setAlignmentX(CENTER_ALIGNMENT)
        textArea.setAlignmentX(CENTER_ALIGNMENT)
        val model = myList.model as DefaultListModel
        thread.onTick = {
            EventQueue.invokeLater { //создаем очередь для обработки действий с объектами в GUI, в том числе со списком
                if (!model.isEmpty) {
                    val x = model.remove(model.size - 1)
                    textArea.append("$x\n")
                }
            }
        }
        startButton.addActionListener {   //слушатель кнопки старт
            thread.start()
        }
        stopButton.addActionListener{   //слушатель кнопки стоп
            thread.stop()
        }
        tempBox.add(startButton) //добавляем всё в коробку
        tempBox.add(stopButton)
        tempBox.add(textArea)
        return tempBox //возвращаем коробку
    }
}