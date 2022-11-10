package factory;

import java.util.Random;

public class IntegerFactory implements UserTypeFactory{
    Random rand = new Random();

    @Override
    public UserType create() {
        return new IntegerType(rand.nextInt(1000) + 1);
    }
}
