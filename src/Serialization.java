import factory.UserFactory;
import factory.UserType;

import java.io.*;
import java.util.ArrayList;

public class Serialization {
    static String returnedValue;

    public static String loadToFile(Heap heap){
        String type = Heap.getObject().typeName();
        try (PrintWriter writer = new PrintWriter("saved_" + type + ".txt")){
            writer.print(type + "\n");
            heap.getHeapArray().forEach(e -> writer.print(e + ","));
            return " > successfully uploaded to file\n";
        } catch (IOException e) {
            return " > failed to upload to file: " + e + "\n";
        }
    }

    public static Heap readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String typeName = br.readLine();
            String lineOfItems = br.readLine();
            String[] items = lineOfItems.split(",");

            ArrayList<UserType> arrayList = new ArrayList<>();
            for (String item : items) {
                UserType object = UserFactory.getBuilderByName(typeName);
                object.parseValue(item);
                arrayList.add(object);
            }
            returnedValue = " > data uploaded successfully\n";
            return new Heap(typeName, arrayList);
        } catch (Exception e) {
            returnedValue = " > failed to load data from file file\n" + e;
            throw new RuntimeException(e);
        }
    }
}