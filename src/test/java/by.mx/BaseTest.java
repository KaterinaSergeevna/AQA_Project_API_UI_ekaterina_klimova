package by.mx;

import by.mx.ui.SearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTest {
    protected WebDriver driver;
    protected SearchPage searchPage;

    protected final Logger log = LogManager.getLogger(this.getClass());

    @BeforeEach
    public void beforeEach(){
        log.info("=== Запуск нового тест-кейса ===");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        searchPage = new SearchPage(driver);
        searchPage.open();
        log.info("Браузер запущен, страница mx.by открыта.");
    }

    @AfterEach
    public void afterEach(){
        if (driver != null) {
            driver.quit();
        }
        log.info("Браузер успешно закрыт, сессия завершена.");
    }
}
