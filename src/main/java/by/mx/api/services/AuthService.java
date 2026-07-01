package by.mx.api.services;

import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

/**
 * Сервисный слой для инкапсуляции бизнес-логики авторизации на mx.by через API.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);
    private static final String AUTH_URL = "/user/auth";

    /**
     * Извлекает динамический CSRF-токен из главной страницы mx.by
     */
    public static String fetchCsrfToken(RequestSpecification spec) {
        log.info("Запрос главной страницы для извлечения CSRF-токена");
        String pageHTML = given()
                .spec(spec)
                .get("/")
                .then()
                .statusCode(200)
                .extract().asString();

        Pattern pattern = Pattern.compile("name=\"_token\"\\s+value=\"([^\"]+)\"|value=\"([^\"]+)\"\\s+name=\"_token\"");
        Matcher matcher = pattern.matcher(pageHTML);

        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        } else {
            throw new RuntimeException("Критическая ошибка: CSRF-токен не найден в DOM-структуре страницы");
        }
    }

    /**
     * Собирает карту параметров для Form Data запроса авторизации
     */
    public static Map<String, String> buildAuthFormData(String email, String password, String csrfToken) {
        Map<String, String> authData = new HashMap<>();
        authData.put("login", "login");
        authData.put("type", "email_password");
        authData.put("email", email);
        authData.put("password", password);
        authData.put("_token", csrfToken);
        return authData;
    }
}