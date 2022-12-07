package factory;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class IntegerType implements UserType {
    private int value;

    public IntegerType() {
        this.value = 0;
    }

    public IntegerType(int value) {
        this.value = value;
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
    public Object parseValue(@NotNull String ss) {
        if (ss.length() == 0) throw new NullPointerException();
        return value = Integer.parseInt(ss);
    }

    @Override
    public Comparator<UserType> getTypeComparator() {
        return this;
    }

    @Override
    public int compare(UserType o1, UserType o2) {
        return Integer.compare(((IntegerType) o1).value, ((IntegerType) o2).value); //o1 > o2 : 1
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
