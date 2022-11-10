import java.io.*;

public class Serialize {

    public static void loadToFile(Heap heap){
        try {
            FileOutputStream fos = new FileOutputStream("temp.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(heap);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Heap readFromFile(String filename){
        try {
            FileInputStream fis = new FileInputStream("temp.bin");
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (Heap) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
