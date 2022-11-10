package factory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DatetimeType implements UserType, Serializable {

    private int year, month, day, hour, minute, second;
    DatetimeType(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String typeName() {
        return "factory.DatetimeType";
    }

    @Override
    public Object clone() {
        try {
            DatetimeType clone = (DatetimeType) super.clone();
            clone.year = this.year;
            clone.month = this.year;
            clone.day = this.year;
            clone.hour = this.year;
            clone.minute = this.year;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object readValue() {
        return this;
    }

    @Override
    public Object parseValue(String ss) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            format.parse(ss);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String[] string = ss.split("[\\s-:]+");
        year = Integer.parseInt(string[0]);
        month = Integer.parseInt(string[1]);
        day = Integer.parseInt(string[2]);
        hour = Integer.parseInt(string[3]);
        minute = Integer.parseInt(string[4]);
        second = Integer.parseInt(string[5]);

        return this;
    }

    @Override
    public Comparator getTypeComparator() {
        return this;
    }

    @Override
    public int compare(Object o1, Object o2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (sdf.parse(o1.toString()).before(sdf.parse(o2.toString()))) return -1; //o1 > o2
            else return 0;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //return Integer.compare(((factory.DatetimeType) o1).year, ((factory.DatetimeType) o2).year);
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }
}
