package factory;

import java.util.ArrayList;
import java.util.Arrays;

public class UserFactory {
    public static ArrayList<String> getTypeNameList(){
        return new ArrayList<>(Arrays.asList("Integer", "Datetime"));
    }

    public static UserTypeFactory getBuilderByName(String name){
        return switch (name.toLowerCase()) {
            case "integer" -> new IntegerFactory();
            case "datetime" -> new DatetimeFactory();
            default -> null;
        };
    }
}
