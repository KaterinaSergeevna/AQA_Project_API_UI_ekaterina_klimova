package by.mx.ui;

import by.mx.ui.driver.Driver;
import by.mx.ui.page.AuthPage;
import by.mx.ui.page.SearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTest {
    protected SearchPage searchPage;
    protected AuthPage authPage;

    protected final Logger log = LogManager.getLogger(this.getClass());

    @BeforeEach
    public void beforeEach() {
        log.info("=== Запуск нового тест-кейса ===");

        searchPage = new SearchPage();
        authPage = new AuthPage();
    }

    @AfterEach
    public void afterEach() {
        Driver.closeDriver();
    }
}
