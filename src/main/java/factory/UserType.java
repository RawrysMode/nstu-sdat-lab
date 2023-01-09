package factory;

import java.util.Comparator;

public interface UserType extends Comparator<UserType>, Cloneable {
    String typeName();
    Object clone();
    Object create();
    Object readValue();
    Object parseValue(String ss);
    Comparator<UserType> getTypeComparator();
}
