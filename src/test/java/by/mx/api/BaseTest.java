package by.mx.api;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static final String BASE_URL = "https://mx.by";
    protected static SessionFilter session;
    protected String dynamicToken;
    protected RequestSpecification requestSpec;

    @BeforeEach
    public void setUp() {
        if (session == null) {
            session = new SessionFilter();
        }

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.URLENC.withCharset("UTF-8"))
                .addHeader("Accept", "application/json")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Referer", BASE_URL)
                .addFilter(session)
                .log(LogDetail.URI)
                .log(LogDetail.PARAMS)
                .build();

        dynamicToken = getToken();
    }

    protected String getToken() {
        String pageHTML = given()
                .spec(requestSpec)
                .get("/")
                .then()
                .statusCode(200)
                .extract().asString();

        try {
            return pageHTML.split("name=\"_token\" value=\"")[1].split("\"")[0];
        }
        catch (Exception e){
            throw new RuntimeException("Не удалось извлечь CSRF токен из страницы mx.by", e);
        }
    }

    protected Map<String, String> getAuthData(User user) {
        Map<String, String> authData = new HashMap<>();
        authData.put("login", "login");
        authData.put("type", "email_password");
        authData.put("email", user.getEmail());
        authData.put("password", user.getPassword());
        authData.put("_token", dynamicToken);
        return authData;
    }
}
