package modules;

import factory.UserType;

import java.util.List;

public interface IHeap {
    String getLanguageType();
    StringBuilder printHeap();
    StringBuilder insertNode(UserType userType);
    StringBuilder insertNode(int index, UserType userType);
    StringBuilder removeNode(int index);
    void displaceUp(int index);
    void displaceDown(int n, int index);
    StringBuilder sort();
    StringBuilder sortToMaxHeap();
    StringBuilder getElementByIndex(int index);
    StringBuilder printArray();
    List<UserType> getHeapArray();
    void setCurrentSizeToZero();
}
