public class Main {
    public static void main(String[] args) {
        testDatetimeType();
        //testIntegerType();
    }

    static void testDatetimeType() {
        Heap heap = new Heap("datetime");
        Heap heap2;

        heap.insertNode("2027-12-03 15:20:30");
        heap.insertNode("2022-12-02 15:20:30");
        heap.insertNode("2014-12-02 15:20:30");
        heap.insertNode("2023-12-02 15:20:30");
        heap.insertNode("2024-12-02 15:20:30");
        heap.insertNode("2025-12-02 15:20:30");
        heap.insertNode("2026-12-02 15:20:30");
        heap.insertNode("2027-12-02 15:20:30");

        heap.printHeap();

        Serialize.loadToFile(heap);
        heap2 = Serialize.readFromFile("temp.bin");

        heap2.printHeap();
    }

    static void testIntegerType() {
        Heap heap = new Heap("integer");

        heap.insertNode("920");
        heap.insertNode("120");
        heap.insertNode("13");
        heap.insertNode("0");
        heap.insertNode("20");
        heap.insertNode("9000");
        heap.insertNode("-12");
        heap.insertNode("-58");

        Serialize.loadToFile(heap);

        heap = Serialize.readFromFile("temp.bin");
        heap.printHeap();
    }
}