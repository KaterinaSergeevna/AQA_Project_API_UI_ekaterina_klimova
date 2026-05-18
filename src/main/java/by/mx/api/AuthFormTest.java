package by.mx.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.filter.session.SessionFilter;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthFormTest {
    private SessionFilter session;
    private String dynamicToken;
    private RequestSpecification requestSpec;
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

        String pageHTML = given()
                .spec(requestSpec)
                .get("/")
                .then()
                .extract().asString();

        dynamicToken = pageHTML.split("name=\"_token\" value=\"")[1].split("\"")[0];

    }

    @Test
    public void testWithIncorrectCreds() {
        Map<String, String> authData = Map.of(
                "login", "login",
                "type", "email_password",
                "email", email,
                "password", "fdwfefwef",
                "_token", dynamicToken
        );

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
    public void testWithEmptyPassword() {
        Map<String, String> authData = Map.of(
                "login", "login",
                "type", "email_password",
                "email", email,
                "password", "",
                "_token", dynamicToken
        );

        given()
                .spec(requestSpec)
                .formParams(authData)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL)
                .then()
                .log().body()
                .statusCode(422)
                .body("errors.password[0]", equalTo("Поле Пароль обязательно для заполнения."));;
    }

    @Test
    public void testWithEmptyEmail() {
        Map<String, String> authData = Map.of(
                "login", "login",
                "type", "email_password",
                "email", "",
                "password", "fdwfefwef",
                "_token", dynamicToken
        );

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
    public void testWithInvalidEmail() {
        Map<String, String> authData = Map.of(
                "login", "login",
                "type", "email_password",
                "email", "tratata.com",
                "password", "fdwfefwef",
                "_token", dynamicToken
        );

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