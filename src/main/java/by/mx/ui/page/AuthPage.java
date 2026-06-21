package by.mx.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AuthPage extends BasePage {
    private final String URL_PATH = "/";

    private final By buttonEnter = By.cssSelector("div.item span[data-auth-info]");
    private final By inputEmail = By.cssSelector("input[id=\"log_email\"]");
    private final By inputPassword = By.cssSelector("input[id=\"log_password\"]");
    private final By emailContainerWithError = By.xpath("//input[@id='log_email']/ancestor::div[contains(@class, 'data-input-check') and contains(@class, 'has-error')]");
    private final By passwordContainerWithError = By.xpath("//input[@id='log_password']/ancestor::div[contains(@class, 'data-input-check') and contains(@class, 'has-error')]");
    private final By buttonLogin = By.xpath("//button[@data-btn-validate=\"login\"]");
    private final By linkRestorePassword = By.xpath("//a[@id=\"restore_btn\"]");
    private final By titlePersonalAccount = By.xpath("//div[@id=\"authModal\"]//p[@class=\"modal-title\"]");
    private final By labelYourEmail = By.xpath("//div[@id=\"restore\"]//label[@class=\"control-label\"]");
    private final By inputEmailForRecoveryPassword = By.xpath("//input[@id=\"log_email2\"]");

    public AuthPage() {
        super();
    }

    public AuthPage open() {
        super.open(URL_PATH);
        return this;
    }

    public AuthPage clickButtonEnter() {
        clickSecurely(buttonEnter);
        return this;
    }

    public AuthPage setTextToInputEmail(String email) {
        sendKeysSecurely(inputEmail, email);
        return this;
    }

    public AuthPage setTextToInputPassword(String password) {
        sendKeysSecurely(inputPassword, password);
        return this;
    }

    public AuthPage clickButtonLogin(){
        clickSecurely(buttonLogin);
        return this;
    }

    public AuthPage clickLinkRestorePassword(){
        waitForSeconds(1);
        clickSecurely(linkRestorePassword);
        return this;
    }

    public String getTitleRecoveryAccountText(){
        return getElementTextSecurely(titlePersonalAccount);
    }

    public String getLabelYourEmailText(){
        return getElementTextSecurely(labelYourEmail);
    }

    public boolean isEmailFieldInvalid() {
        return !driver.findElements(emailContainerWithError).isEmpty();
    }

    public boolean isPasswordFieldInvalid() {
        return !driver.findElements(passwordContainerWithError).isEmpty();
    }

    public boolean isInputEmailForRecoveryPasswordPresent(){
        wait.until(ExpectedConditions.presenceOfElementLocated(inputEmailForRecoveryPassword));
        return true;
    }
}
