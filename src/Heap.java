import factory.UserFactory;
import factory.UserType;
import factory.UserTypeFactory;

import java.io.Serializable;
import java.util.ArrayList;

public class Heap implements Serializable {
    //private final factory.UserType[] heapArray; // массив со всеми вершинами
    private int currentSize; // количество узлов массиве
    private final String userType;

    ArrayList<UserType> heapArray;

    public Heap(String userType) { // создание пустой пирамиды
        this.userType = userType;
        this.currentSize = 0;
        heapArray = new ArrayList<>();
    }

    public void printHeap() { // отображение перамиды в консоль
        System.out.println("Массив значений: ");

        for (int n = 0; n < currentSize; n++) {
            if (heapArray.get(n) != null) {
                System.out.println(heapArray.get(n).readValue() + " ");
            }
            else {
                System.out.println("-");
            }
        }
        System.out.println();

        int countOfGaps = 32;
        int itemsPerRow = 1;
        int columnNumber = 0; // номер элемента в данной строке
        String lines = "___________________________________________________________________";
        System.out.println(lines);
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
            }
            else { //переход к следующему элементу
                for (int k = 0; k < countOfGaps * 2 - 2; k++) {
                    System.out.print(' '); // добавляем оступы
                }
            }
        }
        System.out.println("\n" + lines); // нижний пункир
    }

    public void insertNode(String string) { // вставка нового значения
        UserTypeFactory userTypeFactory = UserFactory.getBuilderByName(userType);
        UserType object = userTypeFactory.create();
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
}


    /*public void removeNode(int index) { // удалить элемент по индексу массива
        if(index > 0 && currentSize > index) {
            factory.UserType root = heapArray[index];
            heapArray[index] = heapArray[--currentSize]; // задаём элементу с переданным индексом, значение последнего элемента
            heapArray[currentSize] = null;// последний элемент удаляем
            displaceDown(index);// проталкиваем вниз новый элемент, чтобы он должное ему место
        }
    }*/

    /*public boolean changeNode(int index, int newValue) {
        if (index < 0 || currentSize<=index) {
            return false;
        }
        int oldValue = heapArray[index].getValue(); // сохраняем старое значение
        heapArray[index].setValue(newValue); // присваиваем новое

        if (oldValue < newValue) {// если узел повышается
            displaceUp(index);     // выполняется смещение вверх
        }
        else {                  // если понижается
            displaceDown(index);   // смещение вниз
        }
        return true;
    }*/