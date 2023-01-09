package modules.java_module;

import factory.UserFactory;
import factory.UserType;
import modules.GUI;
import modules.IHeap;
import utils.Serialization;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        IHeap iHeap = new Heap();
        new GUI(iHeap);
    }
}