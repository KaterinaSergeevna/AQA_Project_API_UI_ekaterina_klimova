package by.mx.bdd;

import by.mx.ui.driver.DriverManager;
import io.cucumber.java.After;

public class Hooks {
    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }
}
