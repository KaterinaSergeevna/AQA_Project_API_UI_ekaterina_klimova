package by.mx.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Epic("API Тестирование")
@Feature("Авторизация")
public class AuthFormTest extends BaseTest {
    private LoginClient loginClient;

    @BeforeEach
    public void prepareTestData() {
        loginClient = new LoginClient(requestSpec);
    }

    @Test
    @DisplayName("Авторизация с невалидными учетными данными")
    @Story("Негативные сценарии авторизации")
    @Description("Проверяем ответ сервера при вводе случайного несуществующего email и пароля")
    public void testWithIncorrectCreds() {
        User fakeUser = Users.getRandomUser();
        Map<String, String> authData = getAuthData(fakeUser);

        loginClient.sendLoginRequest(authData);

        Assertions.assertEquals(422, loginClient.getStatusCode());
        Assertions.assertEquals("Выбранное значение для E-Mail адрес некорректно.", loginClient.getResponseText());
    }

    @Test
    @DisplayName("Авторизация с пустым полем Пароль")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyPassword() {
        User userWithEmptyPassword = new User(Users.getRandomUser().getEmail(), "");
        Map<String, String> authData = getAuthData(userWithEmptyPassword);

        loginClient.sendLoginRequest(authData);

        Assertions.assertEquals(422, loginClient.getStatusCode());
        Assertions.assertEquals("Поле Пароль обязательно для заполнения.", loginClient.getResponseText());
    }

    @Test
    @DisplayName("Авторизация с пустым полем Email")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyEmail() {
        User userWithEmptyEmail = new User("", Users.getRandomUser().getPassword() );
        Map<String, String> authData = getAuthData(userWithEmptyEmail);

        loginClient.sendLoginRequest(authData);

        Assertions.assertEquals(422, loginClient.getStatusCode());
        Assertions.assertEquals("Поле E-Mail адрес обязательно для заполнения.", loginClient.getResponseText());
    }

    @Test
    @DisplayName("Авторизация с некорректным форматом Email")
    @Story("Негативные сценарии авторизации")
    public void testWithInvalidEmail() {
        User userWithInvalidEmail = new User("tratata.com", Users.getRandomUser().getPassword());
        Map<String, String> authData = getAuthData( userWithInvalidEmail);

        loginClient.sendLoginRequest(authData);

        Assertions.assertEquals(422, loginClient.getStatusCode());
        Assertions.assertEquals("Поле E-Mail адрес должно быть действительным электронным адресом.", loginClient.getResponseText());
    }
}