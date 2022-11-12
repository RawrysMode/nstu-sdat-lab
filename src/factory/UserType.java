package factory;

import java.util.Comparator;

public interface UserType extends Comparator {
    String typeName();
    Object clone();
    Object create();
    Object readValue();
    Object parseValue(String ss);
    Comparator getTypeComparator();
}
