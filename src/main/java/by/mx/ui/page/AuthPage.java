package by.mx.ui.page;

import io.qameta.allure.Step;
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

    @Step("Открываем главную страницу сайта mx.by")
    public AuthPage open() {
        super.open(URL_PATH);
        return this;
    }

    @Step("Нажимаем кнопку Вход")
    public AuthPage clickButtonEnter() {
        clickSecurely(buttonEnter);
        return this;
    }

    @Step("Устанавливаем email")
    public AuthPage setTextToInputEmail(String email) {
        sendKeysSecurely(inputEmail, email);
        return this;
    }

    @Step("Устанавливаем password")
    public AuthPage setTextToInputPassword(String password) {
        sendKeysSecurely(inputPassword, password);
        return this;
    }

    @Step("Нажимаем кнопку Войти")
    public AuthPage clickButtonLogin(){
        clickSecurely(buttonLogin);
        return this;
    }

    @Step("Нажимаем ссылку Восстановить пароль")
    public AuthPage clickLinkRestorePassword(){
        waitForSeconds(1);
        clickSecurely(linkRestorePassword);
        return this;
    }

    @Step("Проверяем заголовок страницы восстановления пароля")
    public String getTitleRecoveryAccountText(){
        return getElementTextSecurely(titlePersonalAccount);
    }

    @Step("Проверяем заголовок поля Email страницы восстановления пароля")
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
