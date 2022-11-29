package utils;

import factory.UserFactory;
import factory.UserType;
import modules.IHeap;

import java.io.*;

public class Serialization {
    public static String returnedValue;

    public static String loadToFile(IHeap heap){
        String type;
        if (!heap.getHeapArray().isEmpty()){
            type = heap.getHeapArray().get(0).typeName();
        } else {
            return " > there is nothing to serialize :(\n";
        }

        try (PrintWriter writer = new PrintWriter("saved_" + type + ".txt")){
            writer.print(type + "\n");
            heap.getHeapArray().forEach(e -> writer.print(e + ","));
            return " > successfully uploaded to file\n";
        } catch (IOException e) {
            return " > failed to upload to file: " + e + "\n";
        }
    }

    public static void readFromFile(String filename, IHeap heap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String typeName = br.readLine();
            String lineOfItems = br.readLine();
            String[] items = lineOfItems.split(",");
            heap.setCurrentSizeToZero();

            for (String item : items) {
                UserType object = UserFactory.getBuilderByName(typeName);
                object.parseValue(item);
                heap.insertNode(object);
            }
            returnedValue = " > data uploaded successfully\n";
        } catch (Exception e) {
            returnedValue = " > failed to load data from file file\n" + e;
            throw new RuntimeException(e);
        }
    }
}