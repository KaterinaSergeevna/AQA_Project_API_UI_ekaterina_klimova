package by.mx.api;


import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected String dynamicToken;
    protected RequestSpecification requestSpec;

    protected String getToken() {
        String pageHTML = given()
                .spec(requestSpec)
                .get("/")
                .then()
                .extract().asString();

        return pageHTML.split("name=\"_token\" value=\"")[1].split("\"")[0];
    }

    protected Map<String, String> getAuthData(String email, String password) {
        Map<String, String> authData = new HashMap<>();
        authData.put("login", "login");
        authData.put("type", "email_password");
        authData.put("email", email);
        authData.put("password", password);
        authData.put("_token", dynamicToken);
        return authData;
    }
}
