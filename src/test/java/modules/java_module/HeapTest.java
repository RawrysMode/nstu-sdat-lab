package modules.java_module;

import factory.IntegerType;
import factory.UserFactory;
import factory.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class HeapTest {
    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

//    public static String createRandomDate(int startYear, int endYear) {
//        int day = createRandomIntBetween(1, 28);
//        int month = createRandomIntBetween(1, 12);
//        int year = createRandomIntBetween(startYear, endYear);
//        return year + "-" + month + "-" + day;
//    }

//    public static ArrayList<String> createDateFilledArray(int numberOfDates) {
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < numberOfDates; i++) {
//            arrayList.add(createRandomDate(1930, 2150));
//        }
//        return arrayList;
//    }

    public static ArrayList<Integer> createIntegerFilledArray(int numberOfInts) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < numberOfInts; i++) {
            arrayList.add(createRandomIntBetween(-99999, 99999));
        }
        return arrayList;
    }

    public static String arrayListToString(ArrayList<UserType> arrayList){
        return arrayList.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    // Тестирование перевода массива в строку
    @Test
    void arrayToStringShouldReturnEqualString() {
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        String string = "";

        for (int element : arrayList) {
            string += element + " ";
        }
        String listString = arrayList.stream().map(Object::toString)
                .collect(Collectors.joining(" ")) + " ";

        Assertions.assertEquals(string, listString);
    }

    // Тестирование пирамидальной сортировки на изначально неупорядоченных, рандомно сгенерированных эелементах
    @Test
    void pyramidSortShouldReturnElementsInRightOrderOnRandomGeneratedValues() {
        ArrayList<UserType> userTypeArrayList = new ArrayList<>();
        ArrayList<UserType> arrayList = new ArrayList<>();

        createIntegerFilledArray(1000000).forEach(e -> {
            UserType userType = UserFactory.getBuilderByName("integer");
            userType.parseValue(String.valueOf(e));
            userTypeArrayList.add(userType);
            arrayList.add(userType);
        });

        // pyramid sort
        Heap heap = new Heap(userTypeArrayList);
        heap.pyramidSort();

        // java sort
        arrayList.sort(arrayList.get(0).getTypeComparator());

        Assertions.assertEquals(
                arrayListToString(arrayList),
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование пирамидальной сортировки на изначально упорядоченных элементах
    @Test
    void pyramidSortTestOnOrderedArray(){
        ArrayList<UserType> arrayList = new ArrayList<>(Arrays.asList(
                new IntegerType(1),
                new IntegerType(2),
                new IntegerType(3),
                new IntegerType(4)
        ));

        Heap heap = new Heap(arrayList);
        heap.pyramidSort();

        Assertions.assertEquals(
                "1 2 3 4",
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки на группе повторяющихся элементов
    @Test
    void pyramidSortShouldReturnElementsInRightOrderOnRepeatingElements(){
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++){
            UserType userType = UserFactory.getBuilderByName("integer");
            userType.parseValue("200");
            heap.insertNode(userType);
        }
        heap.insertNode(new IntegerType());
        heap.pyramidSort();

        Assertions.assertEquals(
                "0 200 200 200 200",
                arrayListToString(heap.getHeapArray()));
    }

    // Тестирование сортировки на двух группах повторяющихся элементов
    @Test
    void pyramidSortShouldReturnElementsInRightOrderOnGroupOfRepeatingElements(){
        Heap heap = new Heap();
        for (int i = 0; i < 4; i++){
            UserType userType = UserFactory.getBuilderByName("integer");
            userType.parseValue("200");
            heap.insertNode(userType);
        }
        for (int i = 0; i < 4; i++){
            UserType userType = UserFactory.getBuilderByName("integer");
            userType.parseValue("300");
            heap.insertNode(userType);
        }
        heap.pyramidSort();

        Assertions.assertEquals(
                "200 200 200 200 300 300 300 300",
                arrayListToString(heap.getHeapArray()));
    }


}