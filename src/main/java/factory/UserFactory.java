package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class UserFactory {
    public static ArrayList<String> getTypeNameList() {
        return new ArrayList<>(Arrays.asList("integer", "date"));
    }

    private static final Map<String, Supplier<UserType>> factoryMap = Map.of(
            "integer", IntegerType::new,
            "date", DateType::new
    );

    public static UserType getBuilderByName(String name) {
        Supplier<UserType> factory = factoryMap.get(name);
        if (factory == null) {
            throw new RuntimeException();
        }
        return factory.get();
    }
}
