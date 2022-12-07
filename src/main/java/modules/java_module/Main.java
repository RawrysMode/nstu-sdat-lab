package modules.java_module;

import factory.UserFactory;
import factory.UserType;
import modules.GUI;
import modules.IHeap;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        IHeap iHeap = new Heap();
        new GUI(iHeap);

        testIntegerType(20);
        /* testing on an increasing number of elements
         * */
//        testIntegerType(1000000);
//        testIntegerType(1200000);
//        testIntegerType(1400000);
//        testIntegerType(1600000);
//        testIntegerType(1800000);
//        testIntegerType(2000000);
//        testIntegerType(2200000);
//        testIntegerType(2400000);
//        testIntegerType(2600000);
//        testIntegerType(2800000);
//        testIntegerType(3000000);
    }

    static void testIntegerType(int numberOfRequiredNodes) {
        int rangeBegin = -10;
        int rangeEnd = 10;
        int difference = rangeEnd - rangeBegin;
        ArrayList<UserType> arrayList = new ArrayList<>();

        for (int i = 0; i < numberOfRequiredNodes; i++) {
            Random random = new Random();
            UserType object = UserFactory.getBuilderByName("integer");
            object.parseValue(String.valueOf(random.nextInt(difference) + rangeBegin));
            arrayList.add(object);
        }

        IHeap heap = new Heap(arrayList);
        heap.getHeapArray().sort(heap.getHeapArray().get(0).getTypeComparator());
        System.out.println(heap.printArray());
    }
}