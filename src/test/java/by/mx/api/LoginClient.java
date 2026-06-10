package by.mx.api;

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
    private Response response;

    public LoginClient(RequestSpecification spec){
        this.spec = spec;
    }

    /**
     * Выполняет POST запрос авторизации с переданными параметрами формы
     * @param formParams Карта с параметрами (email, password, _token и т.д.)
     * @return REST Assured Response объект для дальнейших ассертов
     */
    public Response sendLoginRequest(Map<String, String> formParams) {
        log.info("Отправка API-запроса на авторизацию. URL: {}, Email: {}", AUTH_URL, formParams.get("email"));
        this.response = given()
                .spec(spec)
                .formParams(formParams)
                .queryParam("t", System.currentTimeMillis())
                .when()
                .post(AUTH_URL);
        log.info("Получен ответ от сервера. Статус-код: {}", this.response.getStatusCode());
        log.debug("Тело ответа: {}", this.response.asString());
        return this.response;
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public String getResponseText() {
        String message = response.jsonPath().getString("message");
        log.debug("Извлечено сообщение из JSON: {}", message);
        return message;
    }
}
