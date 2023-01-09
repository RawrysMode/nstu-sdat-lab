package modules.java_module;

public class Counter {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public int increment(){
        return this.counter++;
    }
}
