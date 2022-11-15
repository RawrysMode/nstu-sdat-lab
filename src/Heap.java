import factory.UserType;

import java.util.ArrayList;

public class Heap {
    private int currentSize;
    private final ArrayList<UserType> heapArray;

    public ArrayList<UserType> getHeapArray() {
        return heapArray;
    }

    public Heap() {
        this.heapArray = new ArrayList<>();
        this.currentSize = 0;
    }

    public Heap(ArrayList<UserType> arrayList) {
        this.heapArray = new ArrayList<>(arrayList);
        this.currentSize = arrayList.size();
    }

    public StringBuilder printHeap() {
        StringBuilder stringBuilder = new StringBuilder();
        if (heapArray.size() == 0) {
            return stringBuilder.append(" > heap is empty :( \n");
        }

        int itemsPerRow = 1;
        int columnNumber = 0;

        stringBuilder.append("""

                HEAP:\s
                """);
        for (int i = 0; i < currentSize; i++) {
            stringBuilder.append(heapArray.get(i).readValue()).append("  ");

            if (++columnNumber == itemsPerRow) {
                itemsPerRow *= 2;
                columnNumber = 0;
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n\n");
        System.out.println(stringBuilder);
        return stringBuilder;
    }

    public StringBuilder insertNode(UserType userType) {
        heapArray.add(userType);
        displaceUp(currentSize++);
        return new StringBuilder(" > inserted value: ").append(userType).append("\n");
    }

    public StringBuilder insertNode(int index, UserType userType) {
        if (heapArray.isEmpty()) {
            return this.insertNode(userType);
        }
        heapArray.add(index, userType);
        currentSize++;
        if (userType.getTypeComparator().compare(userType, heapArray.get(index + 1)) < 0) {
            displaceDown(currentSize, index);
        } else {
            displaceUp(index);
        }
        return new StringBuilder(" > value ").append(userType).append(" inserted at index [").append(index).append("] \n");
    }

    public StringBuilder removeNode(int index) {
        if (currentSize == 1 && index == 0) {
            heapArray.clear();
            --currentSize;
            return new StringBuilder(" > now heap is empty :( \n");
        }

        if (index >= 0 && currentSize > index) {
            String s = String.valueOf(heapArray.get(index));
            heapArray.set(index, heapArray.get(--currentSize));
            heapArray.remove(currentSize);
            displaceDown(currentSize, index);
            return new StringBuilder(" > node with index [").append(index).append("] ").append(s).append(" has been removed \n");
        }
        return new StringBuilder(" > something went wrong  \n");
    }

    private void displaceUp(int index) {
        int parentIndex = (index - 1) / 2;
        UserType bottom = heapArray.get(index);
        while (index > 0 && bottom.getTypeComparator().compare(heapArray.get(parentIndex), bottom) < 0) {
            heapArray.set(index, heapArray.get(parentIndex));
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        heapArray.set(index, bottom);
    }

    void displaceDown(int n, int index) {
        int largerChild = index;
        int leftChild = 2 * index + 1;
        int rightChild = leftChild + 1;

        UserType object = heapArray.get(index);
        if (leftChild < n && object.getTypeComparator().compare(heapArray.get(leftChild), heapArray.get(largerChild)) > 0)
            largerChild = leftChild;
        if (rightChild < n && object.getTypeComparator().compare(heapArray.get(rightChild), heapArray.get(largerChild)) > 0)
            largerChild = rightChild;

        if (largerChild != index) {
            heapArray.set(index, heapArray.get(largerChild));
            heapArray.set(largerChild, object);
            displaceDown(n, largerChild);
        }
    }

    public StringBuilder sort() {
        long startTime = System.nanoTime();
        sortToMaxHeap();
        for (int i = currentSize - 1; i >= 0; i--) {
            UserType object = heapArray.get(0);
            heapArray.set(0, heapArray.get(i));
            heapArray.set(i, object);
            displaceDown(i, 0);
        }
        long elapsedTime = System.nanoTime() - startTime;
        return new StringBuilder(" > elements sorted: ")
                .append(currentSize).append(" | ")
                .append(elapsedTime / 1000000)
                .append(" milliseconds. \n");
    }

    public StringBuilder sortToMaxHeap(){
        for (int i = currentSize / 2 - 1; i >= 0; i--) {
            displaceDown(currentSize, i);
        }
        return new StringBuilder(" > the heap has been sorted to max-heap\n");
    }

    public StringBuilder getElementByIndex(int index) {
        if (heapArray.isEmpty()) return new StringBuilder(" > heap is empty \n");
        return new StringBuilder(" > returned node with index [").append(index).append("]: ").append(heapArray.get(index)).append("\n");
    }

    public StringBuilder printArray() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("\nArray: [");
        heapArray.forEach(e -> stringBuilder.append(" ").append(e));
        stringBuilder.append(" ] | heap size: ").append(heapArray.size()).append("\n");
        return stringBuilder;
    }
}
