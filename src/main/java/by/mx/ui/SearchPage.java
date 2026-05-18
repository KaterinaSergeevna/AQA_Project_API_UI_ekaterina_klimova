package by.mx.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {
    private final String URL = "https://mx.by/";

    private final String INPUT_SEARCH = "//div[@class=\"input_serch\"]/input";
    private final String BUTTON_FIND = "//div[@class=\"fixed-search-button ok-search__btn\"]";
    private final String TITLE_EXPAND_SEARCH = "//section[@class=\"l-content\"]/h1/span";
    private final String INPUT_KEY_WORDS = "//input[@id=\"findword\"]";
    private final String INPUT_PRICE_FROM = "//input[@name=\"price_before_new\"]";
    private final String INPUT_PRICE_TO = "//input[@name=\"price_after_new\"]";
    private final String CHECKBOX_ONPLACE = "//label[@for=\"checkbox-group\"]/i";
    private final String CHECKBOX_SALE = "//label[@for=\"checkbox-group1\"]/i";
    private final String BUTTON_EXPAND_FIND = "//button[@id=\"find_submit\"]";
    private final String BUTTON_CLEAR = "//span[@class=\"ok-btn btn-reset -width-full -normal\"]";

    public void open() {
        driver.get(URL);
    }

    public SearchPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getExpandSearchTitleText(){
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .withMessage("заголовок не найден за отведенное время");

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TITLE_EXPAND_SEARCH)));
        return element.getText();
    }

    public void setInputSearchText(String text){
        driver.findElement(By.xpath(INPUT_SEARCH)).sendKeys(text);
    }

    public void clickSearchButton(){
        driver.findElement(By.xpath(BUTTON_FIND)).click();
    }

    public void setKeyWordsInput(String text){
        driver.findElement(By.xpath(INPUT_KEY_WORDS)).sendKeys(text);
    }

    public void setPriceFromToInput(String from, String to){
        driver.findElement(By.xpath(INPUT_PRICE_FROM)).sendKeys(from);
        driver.findElement(By.xpath(INPUT_PRICE_TO)).sendKeys(to);
    }

    public void checkOnplaceCheckbox(){
        driver.findElement(By.xpath(CHECKBOX_ONPLACE)).click();
    }

    public void checkSaleCheckbox(){
        driver.findElement(By.xpath(CHECKBOX_SALE)).click();
    }

    public void clickExpandSearchButton(){
        driver.findElement(By.xpath(BUTTON_EXPAND_FIND)).click();
    }

    public void clickClearButton(){
        driver.findElement(By.xpath(BUTTON_CLEAR)).click();
    }
}
