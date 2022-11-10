import factory.UserFactory;
import factory.UserType;
import factory.UserTypeFactory;

import java.io.Serializable;
import java.util.ArrayList;

public class Heap implements Serializable {
    //private final factory.UserType[] heapArray; // массив со всеми вершинами
    private int currentSize; // количество узлов массиве
    UserTypeFactory userTypeFactory;
    UserType object;

    ArrayList<UserType> heapArray;

    public Heap(String userType) { // создание пустой пирамиды
        this.currentSize = 0;
        this.heapArray = new ArrayList<>();
        userTypeFactory = UserFactory.getBuilderByName(userType);
    }

    public void printHeap() { // отображение перамиды в консоль
        System.out.print("Array: [");
        for (int n = 0; n < currentSize; n++) {
            if (heapArray.get(n) != null) {
                System.out.print(" " + heapArray.get(n).readValue());
            } else {
                System.out.print("-");
            }
        }
        System.out.println(" ]");

        int countOfGaps = 32;
        int itemsPerRow = 1;
        int columnNumber = 0; // номер элемента в данной строке

        System.out.println("\n" + "HEAP: ");
        for (int i = 0; i < currentSize; i++) {
            if (columnNumber == 0) {  // проверяем первый элемент ли в текущей строке
                for (int k = 0; k < countOfGaps; k++) { // добавляем предшествующие пробелы
                    System.out.print(' ');
                }
            }

            System.out.print(heapArray.get(i).readValue());// выводим в консоль значение вершины

            if (++columnNumber == itemsPerRow) { // проверяем последний ли элемент в строке
                countOfGaps /= 2; // уменьшаем количество оступов применяемое для следующей строки
                itemsPerRow *= 2; // указываем, что элементов может быть вдвое больше
                columnNumber = 0; // сбрасываем счётчик для текущего элемента строки
                System.out.println(); // переходим на нову строку
            } else { //переход к следующему элементу
                for (int k = 0; k < countOfGaps * 2 - 2; k++) {
                    System.out.print(' '); // добавляем оступы
                }
            }
        }
        System.out.println();
    }

    public void insertNode(String string) { // вставка нового значения
        object = userTypeFactory.create();
        object.parseValue(string);

        heapArray.add(object);// вершину задём в самый низ дерева
        displaceUp(currentSize++);// пытаемся поднять вершину, если значение вершины позволяет
    }


    //heapArray[parentIndex].getValue() < bottom.getValue()
    private void displaceUp(int index) { //смещение вверх
        int parentIndex = (index - 1) / 2; // узнаем индекс родителя
        UserType bottom = heapArray.get(index); // берем элемент
        while (index > 0 && bottom.getTypeComparator().compare(heapArray.get(parentIndex), bottom) < 0) {// если родительский элемент меньше
            heapArray.set(index, heapArray.get(parentIndex));
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;// берем новый родительский индекс и повторяем сравнение элементов
        }
        heapArray.set(index, bottom);
    }

    private void displaceDown(int index) {// смещение вниз
        int largerChild;
        UserType top = heapArray.get(index); // сохранение корня, пока у узла есть хотя бы один потомок
        while (index < currentSize / 2) {// если данное условие не выполняется то элемент уже в самом низу пирамиды
            int leftChild = 2 * index + 1; // вычисляем индексы в массиве для левого узла ребенка
            int rightChild = leftChild + 1;// и правого
            //heapArray[leftChild].getValue() < heapArray[rightChild].getValue()
            if (rightChild < currentSize && top.getTypeComparator().compare(heapArray.get(leftChild), heapArray.get(rightChild)) < 0) {
                largerChild = rightChild;
            }// вычисляем ребенка вершину с наибольшим числовым значением
            else {
                largerChild = leftChild;
            }

            if (top.getTypeComparator().compare(top, heapArray.get(largerChild)) >= 0) {// если значение вершины больше или равно
                //значени его наибольшего ребенка
                break;// то выходим из метода
            }

            heapArray.set(index, heapArray.get(leftChild));
            index = largerChild; // текущий индекс переходит вниз
        }
        heapArray.set(index, top);
    }

    public UserType removeNode(int index) { // удалить элемент по индексу массива
        if (index >= 0 && currentSize >= index) {
            UserType root = heapArray.get(index);
            heapArray.set(index, heapArray.get(--currentSize)); // задаём элементу с переданным индексом, значение последнего элемента
            heapArray.remove(currentSize);
            //heapArray.set(currentSize, null);
            displaceDown(index);// проталкиваем вниз новый элемент, чтобы он должное ему место
            return root;
        }
        return null;
    }

    public boolean changeNode(int index, String newValue) {
        if (index < 0 || currentSize <= index) {
            return false;
        }

        object = userTypeFactory.create();
        object.parseValue(newValue);

        UserType oldValue = heapArray.get(index); // сохраняем старое значение
        heapArray.set(index, object); // присваиваем новое

        if(object.getTypeComparator().compare(oldValue, object) < 0){
            displaceUp(index);
        } else {
            displaceDown(index);
        }
        return true;
    }

    public void getByIndex(int index){
        System.out.println("\n" + "returned value index[" + index + "]: " + heapArray.get(index) + "\n");
    }

}



