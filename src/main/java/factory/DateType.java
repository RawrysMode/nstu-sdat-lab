package factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DateType implements UserType {
    private int year, month, day;
    public DateType() {
        this.year = 1;
        this.month = 1;
        this.day = 1;
    }

    @Override
    public String typeName() {
        return "date";
    }

    @Override
    public Object clone() {
        try {
            DateType clone = (DateType) super.clone();
            clone.year = this.year;
            clone.month = this.year;
            clone.day = this.year;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object create() {
        return new DateType();
    }

    @Override
    public Object readValue() {
        return this;
    }

    @Override
    public Object parseValue(String ss) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            format.parse(ss);
        } catch (ParseException e) {
            throw new RuntimeException("Something went wrong: " + e);
        }

        String[] string = ss.split("-");
        this.year = Integer.parseInt(string[0]);
        this.month = Integer.parseInt(string[1]);
        this.day = Integer.parseInt(string[2]);
        return this;
    }

    @Override
    public Comparator<UserType> getTypeComparator() {
        return this;
    }

    @Override
    public int compare(UserType o1, UserType o2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (sdf.parse(o1.toString()).before(sdf.parse(o2.toString()))) return -1; //o1 > o2 : 1
            if (sdf.parse(o2.toString()).before(sdf.parse(o1.toString()))) return 1; //o1 > o2 : 1
            else return 0;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}