import factory.UserFactory;
import factory.UserType;

import java.util.ArrayList;

public class Heap {
    private int currentSize;
    private static UserType object;
    private final ArrayList<UserType> heapArray;

    public static UserType getObject() {
        return object;
    }

    public ArrayList<UserType> getHeapArray() {
        return heapArray;
    }

    public Heap(String userType) {
        this.currentSize = 0;
        this.heapArray = new ArrayList<>();
        object = UserFactory.getBuilderByName(userType);
    }

    public Heap(String userType, ArrayList<UserType> arrayList){
        this.currentSize = arrayList.size();
        this.heapArray = new ArrayList<>(arrayList);
        object = UserFactory.getBuilderByName(userType);
    }

    public StringBuilder printHeap() {
        StringBuilder stringBuilder = new StringBuilder();
        if (heapArray.size() == 0){
            return stringBuilder.append(" > heap is empty :( \n");
        }

        stringBuilder.append("\nArray: [");
        heapArray.forEach(e -> stringBuilder.append(" ").append(e));
        stringBuilder.append(" ] | heap size: ").append(heapArray.size()).append("\n");

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

    public StringBuilder insertNode(String string) {
        object = UserFactory.getBuilderByName(object.typeName());
        object.parseValue(string);
        heapArray.add(object);
        displaceUp(currentSize++);
        return new StringBuilder(" > inserted value: ").append(string).append("\n");
    }

    public StringBuilder insertNode(int index, String value){
        if (heapArray.isEmpty()){
            return this.insertNode(value);
        }

        object = UserFactory.getBuilderByName(object.typeName());
        object.parseValue(value);
        heapArray.add(index, object);
        currentSize++;
        if(object.getTypeComparator().compare(object, heapArray.get(index + 1)) < 0){
            displaceDown(index);
        } else {
            displaceUp(index);
        }
        return new StringBuilder(" > value ").append(value).append(" inserted at index [").append(index).append("] \n");
    }

    public StringBuilder removeNode(int index) {
        if (currentSize == 1 && index == 0){
            heapArray.clear();
            --currentSize;
            return new StringBuilder(" > now heap is empty :( \n");
        }

        if (index >= 0 && currentSize > index) {
            String s = String.valueOf(heapArray.get(index));
            heapArray.set(index, heapArray.get(--currentSize));
            heapArray.remove(currentSize);
            displaceDown(index);
            return new StringBuilder(" > node with index [").append(index).append("] ").append(s).append(" has been removed \n");
        }
        return new StringBuilder(" > heap is already empty \n");
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

    private void displaceDown(int index) {
        int largerChild;
        UserType top = heapArray.get(index);
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;

            if (rightChild < currentSize && top.getTypeComparator().compare(heapArray.get(leftChild), heapArray.get(rightChild)) < 0) {
                largerChild = rightChild;
            }
            else {
                largerChild = leftChild;
            }

            if (top.getTypeComparator().compare(top, heapArray.get(largerChild)) >= 0) {
                break;
            }

            heapArray.set(index, heapArray.get(largerChild));
            index = largerChild;
        }
        heapArray.set(index, top);
    }

    public StringBuilder getElementByIndex(int index){
        if(heapArray.isEmpty()) return new StringBuilder(" > heap is empty \n");
        return new StringBuilder(" > returned node with index [").append(index).append("]: ").append(heapArray.get(index)).append("\n");
    }
}
