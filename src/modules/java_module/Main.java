package modules.java_module;

import factory.UserFactory;
import factory.UserType;
import modules.IHeap;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //new HeapManagerGUI();

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

        IHeap heap = new Heap(arrayList);
        System.out.print(heap.sort());
    }
}