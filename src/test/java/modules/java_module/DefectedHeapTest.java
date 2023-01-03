package modules.java_module;

import factory.IntegerType;
import factory.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DefectedHeapTest {

    public static ArrayList<UserType> createIntegerFilledArray(int numberOfInts) {
        ArrayList<UserType> arrayList = new ArrayList<>();
        for (int i = 0; i < numberOfInts; i++) {
            arrayList.add(new IntegerType().create());
        }
        return arrayList;
    }

    public static String arrayListToString(ArrayList<UserType> arrayList) {
        return arrayList.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    // Тестирование пирамидальной сортировки на изначально неупорядоченных, рандомно сгенерированных эелементах
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnInitiallyUnorderedRandomSetOfValues() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);

        Heap heap = new Heap(arrayList);
        heap.defectedPyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование пирамидальной сортировки на изначально упорядоченных элементах
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnInitiallyOrderedSetOfValues() {
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++) {
            heap.insertNode(new IntegerType(i));
        }
        heap.defectedPyramidSort();

        Assertions.assertEquals(
                "0 1 2 3",
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки на группе повторяющихся элементов
    @Test
    void pyramidSortingShouldReturnCorrectArrayOnGroupOfRepeatingElements() {
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++) {
            heap.insertNode(new IntegerType(200));
        }
        heap.insertNode(new IntegerType(0));
        heap.defectedPyramidSort();

        Assertions.assertEquals(
                "0 200 200 200 200",
                arrayListToString(heap.getHeapArray()));
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
        heap.defectedPyramidSort();

        Assertions.assertEquals(
                "200 200 200 200 300 300 300 300",
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки при экстремальном значении в начале
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheBeginning() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(0, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.defectedPyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки при экстремальном значении в конце
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheEnding() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.defectedPyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки при экстремальном значении в середине
    @Test
    void pyramidSortingShouldReturnCorrectArrayWhenExtremeValueAtTheMiddle() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(arrayList.size() / 2, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.defectedPyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки при нескольких экстремальных значениях
    @Test
    void pyramidSortingShouldReturnCorrectArrayForMultipleExtremeValues() {
        ArrayList<UserType> arrayList = createIntegerFilledArray(100000);
        arrayList.add(0, new IntegerType(Integer.MAX_VALUE));
        arrayList.add(new IntegerType(Integer.MAX_VALUE));
        arrayList.add(arrayList.size() / 2, new IntegerType(Integer.MAX_VALUE));

        Heap heap = new Heap(arrayList);
        heap.defectedPyramidSort();
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }
}
