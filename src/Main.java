import factory.UserFactory;
import factory.UserType;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new HeapManagerGUI();

        /* testing on an increasing number of elements
        * */
        testIntegerType(1000000);
        testIntegerType(1200000);
        testIntegerType(1400000);
        testIntegerType(1600000);
        testIntegerType(1800000);
        testIntegerType(2000000);
        testIntegerType(2200000);
        testIntegerType(2400000);
        testIntegerType(2600000);
        testIntegerType(2800000);
        testIntegerType(3000000);
    }
    
    static void testIntegerType(int numberOfRequiredNodes) {
        int rangeBegin = -99999;
        int rangeEnd = 99999;
        int difference = rangeEnd - rangeBegin;
        ArrayList<UserType> arrayList = new ArrayList<>();

        for (int i = 0; i < numberOfRequiredNodes; i++){
            Random random = new Random();
            UserType object = UserFactory.getBuilderByName("integer");
            object.parseValue(String.valueOf(random.nextInt(difference) + rangeBegin));
            arrayList.add(object);
        }

        Heap heap = new Heap(arrayList);
        System.out.println(heap.sort());
    }

//    static void testIntegerType() {
//        int numberOfRequiredNodes = 10;
//        int rangeBegin = -99999;
//        int rangeEnd = 99999;
//        int difference = rangeEnd - rangeBegin;
//        Heap heap = new Heap();
//        for (int i = 0; i < numberOfRequiredNodes; i++){
//            Random random = new Random();
//            UserType object = UserFactory.getBuilderByName("integer");
//            object.parseValue(String.valueOf(random.nextInt(difference) + rangeBegin));
//            heap.insertNode(object);
//        }
//        heap.printHeap();
//
//        System.out.println(heap.removeNode(0));
//        heap.printHeap();
//        System.out.println(heap.removeNode(2));
//        heap.printHeap();
//
//        System.out.println(heap.getElementByIndex(0));
//        System.out.println(heap.getElementByIndex(2));
//        heap.printHeap();
//
//        System.out.println(Serialization.loadToFile(heap));
//
//        heap = Serialization.readFromFile("saved_integer.txt");
//        System.out.println(Serialization.returnedValue);
//        heap.printHeap();
//    }
}