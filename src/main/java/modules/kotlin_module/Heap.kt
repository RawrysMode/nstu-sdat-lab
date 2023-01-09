package modules.kotlin_module

import factory.UserType
import modules.IHeap
import java.util.*

class Heap (private var heapArray: ArrayList<UserType> = ArrayList(), private var currentSize: Int = 0) : IHeap {
    constructor(arrayList: ArrayList<UserType>) : this() {
        heapArray = ArrayList(arrayList)
        currentSize = arrayList.size
    }

    override fun getLanguageType(): String {
        return "Kotlin"
    }

    override fun getHeapArray(): List<UserType> {
        return heapArray
    }

    override fun insertNode(userType: UserType): StringBuilder? {
        heapArray.add(userType)
        displaceUp(currentSize++)
        return StringBuilder(" > inserted value: ")
                .append(userType)
                .append("\n")
    }

    override fun insertNode(index: Int, userType: UserType): StringBuilder? {
        if (heapArray.isEmpty()) {
            return this.insertNode(userType)
        }
        heapArray.add(index, userType)
        currentSize++
        if (userType.typeComparator.compare(userType, heapArray[index + 1]) < 0) {
            displaceDown(currentSize, index)
        } else {
            displaceUp(index)
        }
        return StringBuilder(" > value ")
                .append(userType)
                .append(" inserted at index [")
                .append(index)
                .append("] \n")
    }

    override fun removeNode(index: Int): StringBuilder? {
        if (currentSize == 1 && index == 0) {
            heapArray.clear()
            --currentSize
            return StringBuilder(" > now heap is empty :( \n")
        }
        if (index in (0 until currentSize)) {
            val s = heapArray[index].toString()
            heapArray[index] = heapArray[--currentSize]
            heapArray.removeAt(currentSize)
            displaceDown(currentSize, index)
            return StringBuilder(" > node with index [")
                    .append(index)
                    .append("] ")
                    .append(s)
                    .append(" has been removed \n")
        }
        return StringBuilder(" > something went wrong  \n")
    }

    override fun displaceUp(_index: Int) {
        var index = _index
        var parentIndex = (index - 1) / 2
        val bottom = heapArray[index]
        while (index > 0 && bottom.typeComparator.compare(heapArray[parentIndex], bottom) < 0) {
            heapArray[index] = heapArray[parentIndex]
            index = parentIndex
            parentIndex = (parentIndex - 1) / 2
        }
        heapArray[index] = bottom
    }

    override fun displaceDown(n: Int, index: Int) {
        var largerChild = index
        val leftChild = 2 * index + 1
        val rightChild = leftChild + 1
        if (leftChild < n && heapArray[index].typeComparator.compare(heapArray[leftChild], heapArray[largerChild]) > 0) largerChild = leftChild
        if (rightChild < n && heapArray[index].typeComparator.compare(heapArray[rightChild], heapArray[largerChild]) > 0) largerChild = rightChild
        if (largerChild != index) {
            Collections.swap(heapArray, index, largerChild)
            displaceDown(n, largerChild)
        }
    }

    override fun pyramidSort(): StringBuilder? {
        val startTime = System.nanoTime()
        sortToMaxHeap()
        for (i in currentSize - 1 downTo 0) {
            Collections.swap(heapArray, 0, i)
            displaceDown(i, 0)
        }
        val elapsedTime = System.nanoTime() - startTime
        return StringBuilder(" > elements sorted: ")
                .append(currentSize)
                .append(" | ")
                .append(elapsedTime / 1000000)
                .append(" milliseconds. \n")
    }

    override fun sortToMaxHeap(): StringBuilder {
        for (i in currentSize / 2 - 1 downTo 0) {
            displaceDown(currentSize, i)
        }
        return StringBuilder(" > the heap has been sorted to max-heap\n")
    }

    override fun getElementByIndex(index: Int): StringBuilder? {
        return if (heapArray.isEmpty()) StringBuilder(" > heap is empty \n")
        else StringBuilder(" > returned node with index [")
                .append(index)
                .append("]: ")
                .append(heapArray[index])
                .append("\n")
    }

    override fun setCurrentSizeToZero() {
        currentSize = 0
        heapArray.clear()
    }

    override fun printArray(): StringBuilder {
        return StringBuilder()
                .append(heapArray.joinToString(", ", "\nArray: [", "] | heap size: "))
                .append(heapArray.size).append("\n")
    }

    override fun printHeap(): StringBuilder? {
        val stringBuilder = StringBuilder()
        if (heapArray.size == 0) return stringBuilder.append(" > heap is empty :( \n")
        var itemsPerRow = 1
        var columnNumber = 0
        stringBuilder.append("\n > Heap: \n")
        for (i in 0 until currentSize) {
            stringBuilder.append(heapArray[i].readValue()).append("  ")
            if (++columnNumber == itemsPerRow) {
                itemsPerRow *= 2
                columnNumber = 0
                stringBuilder.append("\n")
            }
        }
        stringBuilder.append("\n\n")
        return stringBuilder
    }
}