package by.mx.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.filter.session.SessionFilter;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("API Тестирование")
@Feature("Авторизация")
public class AuthFormTest extends BaseTest {
    private SessionFilter session;
    private final String BASE_URL = "https://mx.by";
    private final String AUTH_URL = "/user/auth";
    private String email;

    @BeforeEach
    public void setUp() {
        session = new SessionFilter();
        Faker faker = new Faker();

        email = faker.internet().emailAddress();

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.URLENC.withCharset("UTF-8"))
                .addHeader("Accept", "application/json")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Referer", BASE_URL)
                .addFilter(session)
                .log(LogDetail.PARAMS)
                .build();

        dynamicToken = getToken();
    }

    @Test
    @DisplayName("Авторизация с невалидными учетными данными")
    @Story("Негативные сценарии авторизации")
    @Description("Проверяем ответ сервера при вводе случайного несуществующего email и пароля")
    public void testWithIncorrectCreds() {

        Map<String, String> authData = getAuthData(email, "fdwfefwef");

        given()
                .spec(requestSpec)
                .formParams(authData)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL)
                .then()
                .log().body()
                .statusCode(422)
                .body("message", equalTo("Выбранное значение для E-Mail адрес некорректно."));
    }

    @Test
    @DisplayName("Авторизация с пустым полем Пароль")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyPassword() {

        Map<String, String> authData = getAuthData(email, "");

        given()
                .spec(requestSpec)
                .formParams(authData)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL)
                .then()
                .log().body()
                .statusCode(422)
                .body("errors.password[0]", equalTo("Поле Пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Авторизация с пустым полем Email")
    @Story("Негативные сценарии авторизации")
    public void testWithEmptyEmail() {

        Map<String, String> authData = getAuthData("", "fdwfefwef");

        given()
                .spec(requestSpec)
                .formParams(authData)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL)
                .then()
                .log().body()
                .statusCode(422)
                .body("message", equalTo("Поле E-Mail адрес обязательно для заполнения."));
    }

    @Test
    @DisplayName("Авторизация с некорректным форматом Email")
    @Story("Негативные сценарии авторизации")
    public void testWithInvalidEmail() {

        Map<String, String> authData = getAuthData("tratata.com", "fdwfefwef");

        given()
                .spec(requestSpec)
                .formParams(authData)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL)
                .then()
                .log().body()
                .statusCode(422)
                .body("message", equalTo("Поле E-Mail адрес должно быть действительным электронным адресом."));
    }
}