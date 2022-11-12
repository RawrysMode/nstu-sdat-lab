import java.sql.Timestamp;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new HeapManagerGUI();
        //testDatetimeType();
        //testIntegerType();
    }

    static void testDatetimeType() {
        int numberOfRequiredNodes = 20;
        long rangeBegin = Timestamp.valueOf("1980-01-01 00:00:00").getTime();
        long rangeEnd = Timestamp.valueOf("2050-12-28 23:59:00").getTime();
        long difference = rangeEnd - rangeBegin + 1;
        Heap heap = new Heap("datetime");
        for (int i = 0; i < numberOfRequiredNodes; i++){
            Timestamp rand = new Timestamp(rangeBegin + (long)(Math.random() * difference));
            String s = rand.toString().split("\\.")[0];
            heap.insertNode(s);
        }
        heap.printHeap();

        System.out.println(heap.removeNode(0));
        heap.printHeap();
        System.out.println(heap.removeNode(2));
        heap.printHeap();

        System.out.println(heap.insertNode(15,"2050-12-28 23:59:00"));
        heap.printHeap();
        System.out.println(heap.insertNode(8,"1970-06-11 15:01:01"));
        heap.printHeap();

        System.out.println(heap.getElementByIndex(0));
        System.out.println(heap.getElementByIndex(16));
        heap.printHeap();

        System.out.println(Serialization.loadToFile(heap));

        heap = Serialization.readFromFile("saved_datetime.txt");
        System.out.println(Serialization.returnedValue);
        heap.printHeap();
    }

    static void testIntegerType() {
        int numberOfRequiredNodes = 20;
        int rangeBegin = -500;
        int rangeEnd = 500;
        int difference = rangeEnd - rangeBegin;
        Heap heap = new Heap("integer");
        for (int i = 0; i < numberOfRequiredNodes; i++){
            Random random = new Random();
            int result = random.nextInt(difference) + rangeBegin;
            heap.insertNode(String.valueOf(result));
        }
        heap.printHeap();

        System.out.println(heap.removeNode(0));
        heap.printHeap();
        System.out.println(heap.removeNode(2));
        heap.printHeap();

        System.out.println(heap.insertNode(15,"90"));
        heap.printHeap();
        System.out.println(heap.insertNode(8,"-25"));
        heap.printHeap();

        System.out.println(heap.getElementByIndex(0));
        System.out.println(heap.getElementByIndex(16));
        heap.printHeap();

        System.out.println(Serialization.loadToFile(heap));

        heap = Serialization.readFromFile("saved_integer.txt");
        System.out.println(Serialization.returnedValue);
        heap.printHeap();
    }
}