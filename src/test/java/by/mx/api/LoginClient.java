package by.mx.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;

public class LoginClient {
    private static final String AUTH_URL = "/user/auth";
    private static final Logger log = LogManager.getLogger(LoginClient.class);
    private final RequestSpecification spec;

    public LoginClient(RequestSpecification spec){
        this.spec = spec;
    }

    /**
     * Выполняет POST запрос авторизации с переданными параметрами формы
     * @param formParams Карта с параметрами (email, password, _token и т.д.)
     * @return REST Assured Response объект для дальнейших ассертов
     */
    @Step("Отправка API-запроса на авторизацию с параметрами: {formParams}")
    public Response sendLoginRequest(Map<String, String> formParams) {
        log.info("Отправка API-запроса на авторизацию. URL: {}, Email: {}", AUTH_URL, formParams.get("email"));
        Response response = given()
                .spec(spec)
                .formParams(formParams)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL);
        log.info("Получен ответ от сервера. Статус-код: {}", response.getStatusCode());
        log.debug("Тело ответа: {}", response.asString());
        return response;
    }

    @Step("Получение статус-кода ответа")
    public int getStatusCode(Response response){
        return response.getStatusCode();
    }

    @Step("Извлечение текста сообщения об ошибке из ответа")
    public String getResponseText(Response response) {
        String message = response.jsonPath().getString("message");
        log.debug("Извлечено сообщение из JSON: {}", message);
        return message;
    }
}
