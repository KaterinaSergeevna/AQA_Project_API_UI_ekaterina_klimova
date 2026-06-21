package by.mx.ui.page;

import by.mx.ui.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://mx.by";

    protected final Logger log = LogManager.getLogger(this.getClass());

    public BasePage() {
        this.driver = Driver.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String urlPath) {
        driver.get(BASE_URL + urlPath);
    }

    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void sendKeysSecurely(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected void clickSecurely(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    protected String getInputValueSecurely(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getDomProperty("value");
    }

    protected String getElementTextSecurely(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }
}
