package by.mx.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage extends BasePage {
    private final String URL = "https://mx.by/";

    private final By inputSearch = By.xpath("//div[@class=\"input_serch\"]/input");
    private final By buttonFind = By.xpath("//div[@class=\"fixed-search-button ok-search__btn\"]");
    private final By titleExpandSearch = By.xpath("//section[@class=\"l-content\"]/h1/span");
    private final By inputKeyWords = By.xpath("//input[@id=\"findword\"]");
    private final By inputPriceFrom = By.xpath("//input[@name=\"price_before_new\"]");
    private final By inputPriceTo = By.xpath("//input[@name=\"price_after_new\"]");
    private final By checkboxOnPlace = By.xpath("//label[@for=\"checkbox-group\"]/i");
    private final By checkboxSale = By.xpath("//label[@for=\"checkbox-group1\"]/i");
    private final By buttonExpandSearch = By.xpath("//button[@id=\"find_submit\"]");
    private final By buttonClear = By.xpath("//span[@class=\"ok-btn btn-reset -width-full -normal\"]");

    private final By titleProductNotFound = By.cssSelector("div[data-ok-toggle-el='result'] p.h3");
    private final By titleProductsName = By.cssSelector("div.product-name a span[itemprop='name']");

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

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(titleExpandSearch));
        return element.getText();
    }

    public void setInputSearchText(String text){
        driver.findElement(inputSearch).sendKeys(text);
    }

    public void clickSearchButton(){
        driver.findElement(buttonFind).click();
    }

    public void setKeyWordsInput(String text){
        clickSearchButton();
        driver.findElement(inputKeyWords).sendKeys(text);
    }

    public void setPriceFromToInput(String from, String to){
        driver.findElement(inputPriceFrom).sendKeys(from);
        driver.findElement(inputPriceTo).sendKeys(to);
    }

    public void checkOnplaceCheckbox(){
        driver.findElement(checkboxOnPlace).click();
    }

    public void checkSaleCheckbox(){
        driver.findElement(checkboxSale).click();
    }

    public void clickExpandSearchButton(){
        driver.findElement(buttonExpandSearch).click();
    }

    public void clickClearButton(){
        driver.findElement(buttonClear).click();
    }

    public void findPurchaseWithoutFilter(String purchase){
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
        searchField.click();

        setInputSearchText(purchase);
        clickSearchButton();
    }

    public String getTitleOfSearchResult(){
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(titleProductsName),
                ExpectedConditions.visibilityOfElementLocated(titleProductNotFound)
        ));

        List<WebElement> productsNames = driver.findElements(titleProductsName);

        if(!productsNames.isEmpty()) {
            return productsNames.get(0).getText().trim().toLowerCase();
        }

        List<WebElement> notFoundMessages = driver.findElements(titleProductNotFound);
        if(!notFoundMessages.isEmpty()) {
            return notFoundMessages.get(0).getText().trim().toLowerCase();
        }
        throw new IllegalStateException("Состояние страницы не определено: " +
                "на экране нет ни товаров, ни сообщения о том, что они не найдены.");
    }
}
