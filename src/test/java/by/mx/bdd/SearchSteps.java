package by.mx.bdd;

import by.mx.ui.page.SearchPage;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class SearchSteps {

    private SearchPage searchPage;

    @Given("User opens main page of mx by")
    public void openMainPage() {
        searchPage = new SearchPage();
        searchPage.open();
    }

    @When("User goes to advanced search page")
    public void goToExpandSearch() {
        searchPage.clickButtonSearch();
    }

    @When("User enters keyword {string} to advance search input")
    public void fillKeywords(String text) {
        searchPage.setTextToKeyWordsInput(text);
    }

    @When("User sets price range from {string} to {string}")
    public void fillPriceRange(String from, String to) {
        searchPage.setPriceFromToInput(from, to);
    }

    @When("User clears all search filters")
    public void clickClear() {
        searchPage.clickButtonClear();
    }

    @Then("All fields of advanced search form should be empty")
    public void verifyFieldsAreEmpty() {
        Assertions.assertAll("Проверка очистки всех полей расширенного поиска",
                () -> Assertions.assertEquals("", searchPage.getTextFromInputKeyWords()),
                () -> Assertions.assertEquals("", searchPage.getTextFromInputPriceFrom()),
                () -> Assertions.assertEquals("", searchPage.getTextFromInputPriceTo())
        );
    }


    @Then("Advanced search page title should display {string}")
    public void verifyAdvanceSearchTitle(String expectedTitle) {
        String actualTitle = searchPage.getAdvancedSearchTitleText();
        Assertions.assertEquals(expectedTitle, actualTitle, "The advanced search page title is incorrect!");
    }
}
