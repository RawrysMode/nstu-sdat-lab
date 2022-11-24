package modules.scala_module

import factory.{UserFactory, UserType}
import modules.{GUI, IHeap}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Main extends App {
  val iHeap: IHeap = new Heap()
  new GUI(iHeap)

    // testing on an increasing number of elements
//  testIntegerType(1000000)
//  testIntegerType(1200000)
//  testIntegerType(1400000)
//  testIntegerType(1600000)
//  testIntegerType(1800000)
//  testIntegerType(2000000)
//  testIntegerType(2200000)
//  testIntegerType(2400000)
//  testIntegerType(2600000)
//  testIntegerType(2800000)
//  testIntegerType(3000000)

  def testIntegerType(numberOfRequiredNodes: Int): Unit = {
    val rangeBegin = -99999
    val rangeEnd = 99999
    val difference = rangeEnd - rangeBegin
    val arrayList = new ArrayBuffer[UserType]

    for (_ <- 0 until numberOfRequiredNodes) {
      val value: UserType = UserFactory.getBuilderByName("integer")
      value.parseValue((Random.nextInt(difference) + rangeBegin).toString)
      arrayList += value
    }

    val heap: IHeap = new Heap(arrayList.toArray)
    print(heap.sort)
  }
}


