package factory;

import java.io.Serializable;

public interface UserTypeFactory extends Serializable {
    UserType create();
}
