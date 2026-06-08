package by.mx.ui.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class Driver {
    private static WebDriver driver;

    protected static final Logger log = LogManager.getLogger(Driver.class);

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        log.info("Браузер успешно закрыт, сессия завершена.");
    }

}
