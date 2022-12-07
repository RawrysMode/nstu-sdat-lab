package modules.kotlin_module

import factory.UserFactory
import factory.UserType
import modules.IHeap
import java.util.*

fun main() {
//    val heap: IHeap = Heap()
//    GUI(heap)

        testIntegerType(1000000)
        testIntegerType(1200000)
        testIntegerType(1400000)
        testIntegerType(1600000)
        testIntegerType(1800000)
        testIntegerType(2000000)
        testIntegerType(2200000)
        testIntegerType(2400000)
        testIntegerType(2600000)
        testIntegerType(2800000)
        testIntegerType(3000000)
}

private fun testIntegerType(numberOfRequiredNodes: Int) {
    val rangeBegin = -99999
    val rangeEnd = 99999
    val difference = rangeEnd - rangeBegin
    val arrayList = ArrayList<UserType>()
    for (i in 0 until numberOfRequiredNodes) {
        val random = Random()
        val value = UserFactory.getBuilderByName("integer")
        value.parseValue((random.nextInt(difference) + rangeBegin).toString())
        arrayList.add(value)
    }
    val heap: IHeap = Heap(arrayList)
    print(heap.pyramidSort())
}
