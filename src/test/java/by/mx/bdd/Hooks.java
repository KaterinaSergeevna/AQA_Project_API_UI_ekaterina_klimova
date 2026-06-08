package by.mx.bdd;

import io.cucumber.java.After;
import by.mx.ui.driver.Driver;

public class Hooks {
    @After
    public void tearDown() {
        Driver.closeDriver();
    }
}
