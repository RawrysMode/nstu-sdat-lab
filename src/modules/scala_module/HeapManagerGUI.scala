package modules.scala_module

import factory.UserFactory
import modules.IHeap
import utils.Serialization

import java.awt.event.ActionEvent
import java.awt.{BorderLayout, Color, Component, Container, Dimension, FlowLayout, Font}
import javax.swing.border.TitledBorder
import javax.swing.{BorderFactory, JButton, JComboBox, JFrame, JPanel, JScrollPane, JTextArea, JTextField}
import scala.collection.mutable.ArrayBuffer

class HeapManagerGUI extends JFrame {
  this.setTitle("Scala Heap Manager")

  private val heapOutputFont = new Font("Consolas", Font.PLAIN, 14)
  private val jButtonsContainer: ArrayBuffer[JButton] = new ArrayBuffer[JButton]
  private val jTextFieldsContainer: ArrayBuffer[JTextField] = new ArrayBuffer[JTextField]
  private val heap: IHeap = new Heap
  private val columnSize = 17

  // java.Main frame panel
  val mainFrame = new JPanel
  mainFrame.setLayout(new BorderLayout)
  mainFrame.setBackground(Color.LIGHT_GRAY)

  // Tools menu panel
  val toolsMenuPanel = new JPanel(new FlowLayout)
  toolsMenuPanel.setPreferredSize(new Dimension(200, 0))
  mainFrame.add(toolsMenuPanel, BorderLayout.WEST)

  // Heap output (view) panel
  val heapOutputArea: JTextArea = new JTextArea(0, 30)
  heapOutputArea.setEditable(false)
  heapOutputArea.setFont(heapOutputFont)
  heapOutputArea.setBackground(Color.WHITE)
  mainFrame.add(heapOutputArea, BorderLayout.EAST)
  mainFrame.add(new JScrollPane(heapOutputArea, 20, 31))

  // Combo box
  val userType = new JComboBox[AnyRef](UserFactory.getTypeNameList.toArray)
  userType.setPreferredSize(new Dimension(170, 20))
  toolsMenuPanel.add(userType)
  userType.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      heap.setCurrentSizeToZero()
      heapOutputArea.setText("")
      heapOutputArea
        .append(" > now selected " + userType.getSelectedItem + " type" + "\n")
    }

    foo()
  })

  // Insertion panel
  val nodeInsertionTextField = new JTextField("enter data here", columnSize)
  val nodeInsertionButton = new JButton("insert")
  nodeInsertionButton.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      val value = UserFactory.getBuilderByName(userType.getSelectedItem.asInstanceOf[String])
      value.parseValue(nodeInsertionTextField.getText)
      heapOutputArea
        .append(String.valueOf(heap.insertNode(value)))
    }

    foo()
  })
  jButtonsContainer += nodeInsertionButton
  jTextFieldsContainer += nodeInsertionTextField
  createJPanel("Insert", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel)

  // Insertion at index panel
  val indexTextField = new JTextField("enter index here", columnSize)
  val dataTextField = new JTextField("enter data here", columnSize)
  val nodeByIndexInsertionButton = new JButton("insert")
  nodeByIndexInsertionButton.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      val value = UserFactory.getBuilderByName(userType.getSelectedItem.asInstanceOf[String])
      value.parseValue(dataTextField.getText)
      heapOutputArea
        .append(String.valueOf(
          heap.insertNode(indexTextField.getText.toInt, value)
        ))
    }

    foo()
  })
  jButtonsContainer += nodeByIndexInsertionButton
  jTextFieldsContainer ++= List(indexTextField, dataTextField)
  createJPanel("Insert by index", 115, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel)

  // Node getter and remover
  val nodeGetterTextField: JTextField = new JTextField("enter index here", columnSize)
  val nodeGetterButton: JButton = new JButton("get")
  nodeGetterButton.addActionListener((_: ActionEvent) => heapOutputArea
    .append(String.valueOf(
      heap.getElementByIndex(nodeGetterTextField.getText.toInt)
    )))

  val nodeRemoverButton: JButton = new JButton("remove")
  nodeRemoverButton.addActionListener((_: ActionEvent) => heapOutputArea
    .append(String.valueOf(
      heap.removeNode(nodeGetterTextField.getText.toInt)
    )))
  jButtonsContainer ++= List(nodeGetterButton, nodeRemoverButton)
  jTextFieldsContainer += nodeGetterTextField
  createJPanel("Getter & remover", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel)

  // Additional options panel
  val exportButton: JButton = new JButton("export")
  exportButton.addActionListener((_: ActionEvent) => heapOutputArea
    .append(Serialization.loadToFile(heap)))
  val importButton: JButton = new JButton("import")
  importButton.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      if (userType.getSelectedItem eq "integer") {
        Serialization.readFromFile("saved_integer.txt", heap)
        heapOutputArea.append(Serialization.returnedValue)
      }
      else {
        Serialization.readFromFile("saved_date.txt", heap)
        heapOutputArea.append(Serialization.returnedValue)
      }
    }

    foo()
  })
  val printHeap: JButton = new JButton("print")
  printHeap.addActionListener((_: ActionEvent) => heapOutputArea
    .append(String.valueOf(heap.printHeap())))
  val clearButton: JButton = new JButton("clear log")
  clearButton.addActionListener((_: ActionEvent) => heapOutputArea.setText(""))
  val defaultSortButton: JButton = new JButton("default sort")
  defaultSortButton.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      heapOutputArea.append(String.valueOf(heap.sort))
      heapOutputArea.append(String.valueOf(heap.printArray))
    }

    foo()
  })
  val maxHeapSortButton: JButton = new JButton("max-heap sort")
  maxHeapSortButton.addActionListener((_: ActionEvent) => heapOutputArea
    .append(String.valueOf(heap.sortToMaxHeap)))
  val clearHeapButton: JButton = new JButton("clear heap")
  clearHeapButton.addActionListener((_: ActionEvent) => {
    def foo(): Unit = {
      heap.setCurrentSizeToZero()
      heapOutputArea.append(" > heap empty now\n")
    }

    foo()
  })
  jButtonsContainer ++= List(exportButton, importButton, printHeap, clearButton, defaultSortButton, maxHeapSortButton, clearHeapButton)
  val nullJFiller: ArrayBuffer[JTextField] = new ArrayBuffer[JTextField]
  createJPanel("Additional options", 225, jButtonsContainer, nullJFiller, toolsMenuPanel)

  // JFrame setting
  changeFont(mainFrame, heapOutputFont)
  changeFont(this, heapOutputFont)

  this.setSize(800, 650)
  this.setDefaultCloseOperation(3)
  this.setContentPane(mainFrame)
  this.setVisible(true)

  private def changeFont(component: Component, font: Font): Unit = {
    component.setFont(font)
    component match {
      case container: Container => for (child <- container.getComponents) {
        changeFont(child, font)
      }
      case _ =>
    }
  }

  private def createJPanel(title: String, height: Int, targetButton: ArrayBuffer[JButton], targetTextField: ArrayBuffer[JTextField], targetBox: JPanel): Unit = {
    val jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5))
    jPanel.setPreferredSize(new Dimension(180, height))
    jPanel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createLineBorder(Color.BLACK),
      title,
      TitledBorder.LEFT,
      TitledBorder.TOP,
      heapOutputFont,
      Color.BLACK
    ))

    if (targetTextField.nonEmpty) {
      for (textField <- targetTextField) {
        jPanel.add(textField)
      }
    }

    if (targetButton.nonEmpty) {
      for (button <- targetButton) {
        jPanel.add(button)
      }
    }

    targetBox.add(jPanel)
    jButtonsContainer.clear()
    jTextFieldsContainer.clear()
  }
}
