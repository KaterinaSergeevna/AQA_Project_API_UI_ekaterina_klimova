package by.mx.api;

import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Users {
    private static final Faker faker = new Faker();
    private static final Logger log = LoggerFactory.getLogger(Users.class);

    public static User getRandomUser(){
        String email = faker.internet().emailAddress();
        String password = faker.credentials().password(4, 10);
        log.info("User with email {} and password {}", email, password);
        return new User(email, password);
    }
}
