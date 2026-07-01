package by.mx.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Epic("API Тестирование")
@Feature("Авторизация")
public class AuthFormTest extends ApiBaseTest {
    private LoginClient loginClient;

    @BeforeEach
    public void prepareTestData() {
        loginClient = new LoginClient(getRequestSpec());
    }

    @Test
    @DisplayName("Авторизация с невалидными учетными данными")
    @Story("Негативные сценарии авторизации")
    @Description("Проверяем ответ сервера при вводе случайного несуществующего email и пароля")
    public void testWithIncorrectCreds() {
        User fakeUser = UserFactory.createRandomUser();
        Map<String, String> authData = getAuthData(fakeUser);

        Response response = loginClient.sendLoginRequest(authData);

        Allure.step("Проверить, что сервер вернул ошибку валидации 422", () -> {
            Assertions.assertEquals(422, loginClient.getStatusCode(response));
            Assertions.assertEquals("Выбранное значение для E-Mail адрес некорректно.",
                    loginClient.getResponseText(response));
        });
    }

    @Test
    @DisplayName("Авторизация с пустым полем Пароль")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyPassword() {
        User userWithEmptyPassword = UserFactory.createWithEmptyPassword();
        Map<String, String> authData = getAuthData(userWithEmptyPassword);

        Response response = loginClient.sendLoginRequest(authData);

        Allure.step("Проверить, что сервер вернул ошибку валидации 422", () -> {
            Assertions.assertEquals(422, loginClient.getStatusCode(response));
            Assertions.assertEquals("Поле Пароль обязательно для заполнения.", loginClient.getResponseText(response));
        });
    }

    @Test
    @DisplayName("Авторизация с пустым полем Email")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyEmail() {
        User userWithEmptyEmail = UserFactory.createWithEmptyEmail();
        Map<String, String> authData = getAuthData(userWithEmptyEmail);

        Response response = loginClient.sendLoginRequest(authData);

        Allure.step("Проверить, что сервер вернул ошибку валидации 422", () -> {
            Assertions.assertEquals(422, loginClient.getStatusCode(response));
            Assertions.assertEquals("Поле E-Mail адрес обязательно для заполнения.", loginClient.getResponseText(response));
        });
    }

    @Test
    @DisplayName("Авторизация с некорректным форматом Email")
    @Story("Негативные сценарии авторизации")
    public void testWithInvalidEmail() {
        User userWithInvalidEmail = UserFactory.createWithInvalidEmailFormat();
        Map<String, String> authData = getAuthData( userWithInvalidEmail);

        Response response = loginClient.sendLoginRequest(authData);

        Allure.step("Проверить, что сервер вернул ошибку валидации 422", () -> {
            Assertions.assertEquals(422, loginClient.getStatusCode(response));
            Assertions.assertEquals("Поле E-Mail адрес должно быть действительным электронным адресом.",
                    loginClient.getResponseText(response));
        });
    }
}