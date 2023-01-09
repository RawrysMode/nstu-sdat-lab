package modules.java_module;

import factory.IntegerType;
import factory.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class HeapTest {
    public static ArrayList<UserType> createIntegerFilledArray(int numberOfInts) {
        ArrayList<UserType> arrayList = new ArrayList<>();
        for (int i = 0; i < numberOfInts; i++) {
            arrayList.add(new IntegerType().create());
        }
        return arrayList;
    }

    // Тестирование пирамидальной сортировки на изначально неупорядоченных, рандомно сгенерированных элементах
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnInitiallyUnorderedRandomSetOfValues() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayList.toString(),
                heap.getHeapArray().toString());
    }

    // Тестирование пирамидальной сортировки на изначально упорядоченных элементах
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnInitiallyOrderedSetOfValues() {
        Heap heap = new Heap();
        for (int i = 0; i < 100000; i++) {
            heap.insertNode(new IntegerType().create());
        }
        heap.pyramidSort();

        Assertions.assertEquals(
                heap.getHeapArray().toString(),
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки на группе повторяющихся элементов
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnGroupOfRepeatingElements() {
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++) {
            heap.insertNode(new IntegerType(200));
        }
        heap.insertNode(new IntegerType(0));
        heap.pyramidSort();

        Assertions.assertEquals(
                "[0, 200, 200, 200, 200]",
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки на двух группах повторяющихся элементов
    @Test
    void pyramidSortingShouldReturnElementsInRightOrderOnGroupOfRepeatingElements() {
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++) {
            heap.insertNode(new IntegerType(300));
        }
        for (int i = 0; i < 4; i++) {
            heap.insertNode(new IntegerType(200));
        }
        heap.pyramidSort();

        Assertions.assertEquals(
                "[200, 200, 200, 200, 300, 300, 300, 300]",
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки при экстремальном значении в начале
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheBeginning() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(0, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayList.toString(),
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки при экстремальном значении в конце
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheEnding() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayList.toString(),
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки при экстремальном значении в середине
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheMiddle() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(arrayList.size() / 2, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayList.toString(),
                heap.getHeapArray().toString());
    }

    // Тестирование сортировки при нескольких экстремальных значениях
    @Test
    void pyramidSortingShouldReturnCorrectArrayForMultipleExtremeValues() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(0, new IntegerType(Integer.MAX_VALUE));
        arrayList.add(new IntegerType(Integer.MAX_VALUE));
        arrayList.add(arrayList.size() / 2, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayList.toString(),
                heap.getHeapArray().toString());
    }

    /* testing on an increasing number of elements
     * */
    @Test
    void test() {
        for (int i = 10000; i <= 10240000; i *= 2) {
            Heap heap = new Heap(createIntegerFilledArray(i));
            System.out.println(heap.pyramidSort());
        }
    }
}