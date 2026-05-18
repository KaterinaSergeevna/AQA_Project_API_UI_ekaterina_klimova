package by.mx;

import by.mx.ui.SearchPage;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;
    protected SearchPage searchPage;

    @BeforeEach
    public void beforeEach(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        searchPage = new SearchPage(driver);
        searchPage.open();
    }
}
