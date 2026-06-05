package by.mx.ui.page;

import org.openqa.selenium.By;

public class AuthPage extends BasePage {
    private final String URL_PATH = "/";

    private final By buttonEnter = By.cssSelector("div.item span[data-auth-info]");
    private final By inputEmail = By.cssSelector("input[id=\"log_email\"]");
    private final By inputPassword = By.cssSelector("input[id=\"log_password\"]");

    public AuthPage() {
        super();
    }

    public void open() {
        super.open(URL_PATH);
    }

    public void clickButtonEnter() {
        clickSecurely(buttonEnter);
    }

    public void setTextToInputEmail(String email) {
        sendKeysSecurely(inputEmail, email);
    }

    public void setTextToInputPassword(String password) {
        sendKeysSecurely(inputPassword, password);
    }
}
