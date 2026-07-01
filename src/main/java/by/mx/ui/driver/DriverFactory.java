package by.mx.ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver getDriver(String browserName) {

        if (browserName == null) {
            throw new IllegalArgumentException("Argument \"browserName\" can not be null");
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Browser + \"" + browserName + "\" is not supported");
        }
    }
}
