package by.mx.api;


import by.mx.api.services.AuthService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

public class ApiBaseTest {
    protected static final String BASE_URL = "https://mx.by";
    private static final ThreadLocal<SessionFilter> sessionThreadLocal = ThreadLocal.withInitial(SessionFilter::new);
    private final ThreadLocal<String> dynamicTokenThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<RequestSpecification> requestSpecThreadLocal = new ThreadLocal<>();

    @BeforeEach
    public void setUp() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.URLENC.withCharset("UTF-8"))
                .addHeader("Accept", "application/json")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Referer", BASE_URL)
                .addFilter(sessionThreadLocal.get())
                .log(LogDetail.URI)
                .log(LogDetail.PARAMS)
                .build();

        requestSpecThreadLocal.set(spec);
        String token = AuthService.fetchCsrfToken(spec);
        dynamicTokenThreadLocal.set(token);
    }

    protected RequestSpecification getRequestSpec() {
        return requestSpecThreadLocal.get();
    }

    protected String getDynamicToken() {
        return dynamicTokenThreadLocal.get();
    }

    protected Map<String, String> getAuthData(User user) {
        return AuthService.buildAuthFormData(user.getEmail(), user.getPassword(), getDynamicToken());
    }
}
