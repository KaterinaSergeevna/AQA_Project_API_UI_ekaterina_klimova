package by.mx.api;

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserFactory {
    private static final Faker faker = new Faker();
    private static final Logger log = LogManager.getLogger(UserFactory.class);

    private static String generateValidPassword() {
        return faker.credentials().password(4, 10, true, true);
    }

    private static String generateValidEmail() {
        return faker.internet().emailAddress();
    }

    public static User createRandomUser(){
        String email = generateValidEmail();
        String password = generateValidPassword();
        log.info("Фабрика создала пользователя с email {} и password {}", email, password);
        return new User(email, password);
    }

    public static User createWithEmptyEmail() {
        return new User("", generateValidPassword());
    }

    public static User createWithEmptyPassword() {
        return new User(generateValidEmail(), "");
    }

    public static User createWithInvalidEmailFormat() {
        String invalidEmail = faker.lorem().word() + "invalid-format";
        String validPassword = generateValidPassword();

        log.debug("Фабрика создала пользователя с невалидной почтой: {}", invalidEmail);
        return new User(invalidEmail, validPassword);
    }
}
