package factory;

public class DatetimeFactory implements UserTypeFactory{
    @Override
    public UserType create() {
        return new DatetimeType(1920, 12,3,12,5, 12);
    }
}
