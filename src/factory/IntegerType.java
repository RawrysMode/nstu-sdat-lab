package factory;

import java.io.Serializable;
import java.util.Comparator;

public class IntegerType implements UserType, Serializable {
    private int value;
    public IntegerType() {
    }

    @Override
    public String typeName() {
        return "integer";
    }

    @Override
    public IntegerType clone() {
        try {
            IntegerType clone = (IntegerType) super.clone();
            clone.value = this.value;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object create() {
        return new IntegerType();
    }

    @Override
    public Object readValue() {
        return this.value;
    }

    @Override
    public Object parseValue(String ss) {
        return this.value = Integer.parseInt(ss);
    }

    @Override
    public Comparator getTypeComparator() {
        return this;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(((IntegerType) o1).value, ((IntegerType) o2).value); //o1 > o2 : 1
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
