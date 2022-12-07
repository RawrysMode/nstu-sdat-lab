package modules.scala_module

import factory.UserType
import modules.IHeap

import java.lang.StringBuilder
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.BufferHasAsJava

class Heap extends IHeap {
  private var heapArray: ArrayBuffer[UserType] = new ArrayBuffer[UserType]()
  private var currentSize: Int = 0
  private val languageType = "Scala"

  override def getLanguageType: String = "Scala";

  def this(arrayList: Array[UserType]) {
    this()
    this.heapArray = ArrayBuffer(arrayList: _*)
    this.currentSize = arrayList.length
  }

  def getHeapArray: java.util.List[UserType] = heapArray.asJava

  def insertNode(userType: UserType): StringBuilder = {
    heapArray += userType
    displaceUp({
      currentSize += 1
      currentSize - 1
    })
    new StringBuilder(" > inserted value: ")
      .append(userType)
      .append("\n")
  }

  def insertNode(index: Int, userType: UserType): StringBuilder = {
    if (heapArray.isEmpty) return this.insertNode(userType)
    heapArray.insert(index, userType)
    currentSize += 1
    if (userType.getTypeComparator.compare(userType, heapArray.apply(index + 1)) < 0) displaceDown(currentSize, index)
    else displaceUp(index)
    new StringBuilder(" > value ")
      .append(userType)
      .append(" inserted at index [")
      .append(index)
      .append("] \n")
  }

  def removeNode(index: Int): StringBuilder = {
    if (currentSize == 1 && index == 0) {
      heapArray.clear()
      currentSize -= 1
      return new StringBuilder(" > now heap is empty :( \n")
    }
    if (index >= 0 && currentSize > index) {
      val s = heapArray.apply(index)
      currentSize -= 1
      heapArray(index) = heapArray.apply(currentSize)
      heapArray.remove(currentSize)
      displaceDown(currentSize, index)
      return new StringBuilder(" > node with index [")
        .append(index)
        .append("] ")
        .append(s)
        .append(" has been removed \n")
    }
    new StringBuilder(" > something went wrong  \n")
  }

  override def displaceUp(index: Int): Unit = {
    var i = index
    var parentIndex = (index - 1) / 2
    val bottom = heapArray.apply(index)
    while ( {
      i > 0 && bottom.getTypeComparator.compare(heapArray.apply(parentIndex), bottom) < 0
    }) {
      heapArray(i) = heapArray.apply(parentIndex)
      i = parentIndex
      parentIndex = (parentIndex - 1) / 2
    }
    heapArray(i) = bottom
  }

  @tailrec
  final def displaceDown(n: Int, index: Int): Unit = {
    var largerChild = index
    val leftChild = 2 * index + 1
    val rightChild = leftChild + 1
    val currentObject = heapArray.apply(index)
    if (leftChild < n && currentObject.getTypeComparator.compare(heapArray.apply(leftChild), heapArray.apply(largerChild)) > 0) largerChild = leftChild
    if (rightChild < n && currentObject.getTypeComparator.compare(heapArray.apply(rightChild), heapArray.apply(largerChild)) > 0) largerChild = rightChild
    if (largerChild != index) {
      heapArray(index) = heapArray.apply(largerChild)
      heapArray(largerChild) = currentObject
      displaceDown(n, largerChild)
    }
  }

  def pyramidSort: StringBuilder = {
    val startTime = System.nanoTime
    sortToMaxHeap
    for (i <- currentSize - 1 to 0 by -1) {
      val currentObject = heapArray.apply(0)
      heapArray(0) = heapArray.apply(i)
      heapArray(i) = currentObject
      displaceDown(i, 0)
    }
    val elapsedTime = System.nanoTime - startTime
    new StringBuilder(" > elements sorted: ")
      .append(currentSize)
      .append(" | ")
      .append(elapsedTime / 1000000)
      .append(" milliseconds. \n")
  }

  def sortToMaxHeap: StringBuilder = {
    for (i <- currentSize / 2 - 1 to 0 by -1) {
      displaceDown(currentSize, i)
    }
    new StringBuilder(" > the heap has been sorted to max-heap\n")
  }

  def getElementByIndex(index: Int): StringBuilder = {
    if (heapArray.isEmpty) return new StringBuilder(" > heap is empty \n")
    new StringBuilder(" > returned node with index [")
      .append(index)
      .append("]: ")
      .append(heapArray.apply(index))
      .append("\n")
  }

  override def setCurrentSizeToZero(): Unit = {
    this.currentSize = 0
    heapArray.clear()
  }

  def printArray: StringBuilder = {
    new StringBuilder()
      .append(heapArray.mkString("\n > Array: [", ", ", "] | heap size: "))
      .append(heapArray.size)
      .append("\n")
  }

  override def printHeap: StringBuilder = {
    val stringBuilder = new StringBuilder
    if (heapArray.isEmpty)
      return stringBuilder.append(" > heap is empty :( \n")

    var itemsPerRow = 1
    var columnNumber = 0
    stringBuilder.append("\n > Heap: \n")
    for (i <- 0 until currentSize) {
      stringBuilder.append(heapArray(i).readValue)
        .append("  ")
      if ( {
        columnNumber += 1
        columnNumber
      } == itemsPerRow) {
        itemsPerRow *= 2
        columnNumber = 0
        stringBuilder.append("\n")
      }
    }
    stringBuilder.append("\n\n")
    stringBuilder
  }
}
