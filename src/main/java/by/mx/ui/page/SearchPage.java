package by.mx.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {
    private final String URL_PATH = "/";

    private final By inputSearch = By.xpath("//div[@class=\"input_serch\"]/input");
    private final By buttonSearch = By.xpath("//div[@class=\"fixed-search-button ok-search__btn\"]");
    private final By titleAdvancedSearch = By.xpath("//section[@class=\"l-content\"]/h1/span");
    private final By inputKeyWords = By.xpath("//input[@id=\"findword\"]");
    private final By inputPriceFrom = By.xpath("//input[@name=\"price_before_new\"]");
    private final By inputPriceTo = By.xpath("//input[@name=\"price_after_new\"]");
    private final By checkboxInStock = By.xpath("//label[@for=\"checkbox-group\"]/i");
    private final By checkboxSale = By.xpath("//label[@for=\"checkbox-group1\"]/i");
    private final By buttonAdvancedSearch = By.xpath("//button[@id=\"find_submit\"]");
    private final By buttonClear = By.xpath("//span[@class=\"ok-btn btn-reset -width-full -normal\"]");
    private final By firstProductPrice = By.cssSelector(".products--find div.product-price span.current-price");
    private final By titleProductNotFound = By.cssSelector("div[data-ok-toggle-el='result'] p.h3");
    private final By titleProductsName = By.cssSelector(".products--find div.product-name a span[itemprop='name']");

    public SearchPage() {
        super();
    }

    public void open() {
        super.open(URL_PATH);
    }

    public String getAdvancedSearchTitleText() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(titleAdvancedSearch));
        return element.getText().toLowerCase().trim();
    }

    public void setTextToInputSearch(String text) {
        sendKeysSecurely(inputSearch, text);
    }

    public void clickButtonSearch() {
        clickSecurely(buttonSearch);
    }

    public void setTextToKeyWordsInput(String text) {
        sendKeysSecurely(inputKeyWords, text);
    }

    public void setPriceFromToInput(String from, String to) {
        sendKeysSecurely(inputPriceFrom, from);
        sendKeysSecurely(inputPriceTo, to);
    }

    public void clickCheckboxInStock() {
        clickSecurely(checkboxInStock);
    }

    public void clickCheckboxSale() {
        clickSecurely(checkboxSale);
    }

    public void clickButtonAdvancedSearch() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonAdvancedSearch));
        waitForSeconds(1);
        button.click();
        waitForSeconds(1);
    }

    public void clickButtonClear() {
        clickSecurely(buttonClear);
    }

    public void findPurchaseWithoutFilter(String purchase) {
        clickSecurely(inputSearch);

        setTextToInputSearch(purchase);
        clickButtonSearch();
    }

    public double getPriceOfFirstSearchResult() {
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductPrice));
        String rawPriceText = priceElement.getText().trim();

        String cleanText = rawPriceText.replaceAll("[^0-9,. ]", "").trim();

        if (cleanText.contains(",")) {
            cleanText = cleanText.split(",")[0];
        } else if (cleanText.contains(".")) {
            cleanText = cleanText.split("\\.")[0];
        }

        cleanText = cleanText.replaceAll("\\s+", "");
        return Double.parseDouble(cleanText);
    }

    public String getFirstProductName() {
        log.info("Ожидание появления товаров на UI...");
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(titleProductsName));
        return productName.getText().trim().toLowerCase();
    }

    public String getProductNotFoundMessage() {
        log.info("Ожидание сообщения 'Товар не найден' на UI...");
        WebElement notFoundMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(titleProductNotFound));
        return notFoundMessage.getText().trim().toLowerCase();
    }

    public boolean isProductListEmpty() {
        return driver.findElements(titleProductsName).isEmpty();
    }

    public String getTextFromInputKeyWords() {
        return getInputValueSecurely(inputKeyWords);
    }

    public String getTextFromInputPriceFrom() {
        return getInputValueSecurely(inputPriceFrom);
    }

    public String getTextFromInputPriceTo() {
        return getInputValueSecurely(inputPriceTo);
    }
}
