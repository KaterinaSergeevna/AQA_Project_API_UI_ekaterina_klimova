package by.mx.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@Epic("UI Тестирование")
@Feature("Модуль поиска и фильтрации товаров")
@Link(name = "Интернет-магазин MX.by", url = "https://mx.by")
public class SearchTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Базовый поиск в шапке сайта")
    @Description("Позитивный сценарий: проверка корректности базового поиска при вводе существующего наименования товара")
    @DisplayName("Базовый поиск: валидное значение")
    public void simpleSearchWithValidData() {
        searchPage.open();
        String purchase = "Aqara Smart Door Lock N100 Zigbee";

        Allure.step("Выполнение базового поиска по запросу: " + purchase);
        searchPage.findPurchaseWithoutFilter(purchase);

        Allure.step("Получение и валидация имени первого товара в результатах выдачи");
        String resultSearch = searchPage.getFirstProductName();
        Assertions.assertTrue(resultSearch.contains(purchase.toLowerCase()),
                "Название товара на UI не соответствует поисковому запросу!");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Базовый поиск в шапке сайта")
    @Description("Негативный сценарий: проверка отображения заглушки и сообщения системы при отсутствии товаров на складе")
    @DisplayName("Базовый поиск: невалидное значение")
    public void simpleSearchWithInvalidData() {
        searchPage.open();
        String purchase = "Aqara Smart Door Lock N100 Zigbee2";

        Allure.step("Выполнение базового поиска по несуществующему запросу: " + purchase);
        searchPage.findPurchaseWithoutFilter(purchase);

        Allure.step("Проверка отображения системного сообщения об отсутствии результатов");
        String resultSearch = searchPage.getProductNotFoundMessage();
        Assertions.assertTrue(resultSearch.startsWith("не найдено ни одного товара"),
                "Штатное сообщение об отсутствии товаров не отобразилось!");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Форма расширенного поиска")
    @Description("Проверка перехода пользователя на страницу расширенного поиска при клике на пустую кнопку поиска в шапке")
    @DisplayName("Расширенный поиск: переход на страницу формы")
    public void openExpandSearch() {
        searchPage.open();
        Allure.step("Клик по кнопке поиска в шапке для открытия расширенной формы");
        searchPage.clickButtonSearch();

        Allure.step("Валидация заголовка открывшейся страницы");
        Assertions.assertEquals("расширенный поиск", searchPage.getAdvancedSearchTitleText(),
                "Заголовок страницы расширенного поиска некорректен!");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Форма расширенного поиска")
    @Description("Комплексный сценарий: заполнение поисковых фильтров по ключевому слову, диапазону цен и чекбоксу 'В наличии' с последующей математической валидацией типов данных double")
    @DisplayName("Расширенный поиск: фильтрация по категории, цене и наличию")
    public void checkFiltersOfPriceRange() {
        searchPage.open();
        String product = "Отпариватель";
        double priceFrom = 100.0;
        double priceTo = 200.0;

        log.info("=== СТАРТ ТЕСТА: Фильтрация по названию '{}' и диапазону цен {}-{} руб. ===", product, priceFrom, priceTo);

        Allure.step("Открытие формы расширенного поиска");
        searchPage.clickButtonSearch();

        Allure.step("Заполнение ключевого слова: " + product);
        searchPage.setTextToKeyWordsInput(product);

        Allure.step(String.format("Установка ценового диапазона: от %.2f до %.2f руб.", priceFrom, priceTo));
        searchPage.setPriceFromToInput(String.valueOf((int) priceFrom), String.valueOf((int) priceTo));

        Allure.step("Активация чекбокса 'В наличии' (In Stock)");
        searchPage.clickCheckboxInStock();

        Allure.step("Нажатие кнопки применения расширенных фильтров");
        searchPage.clickButtonAdvancedSearch();

        Allure.step("Валидация соответствия названия первого товара поисковому запросу");
        String actualTitle = searchPage.getFirstProductName();
        Assertions.assertTrue(actualTitle.contains(product.toLowerCase()),
                String.format("Найденный товар '%s' не содержит ключевое слово '%s'", actualTitle, product));
        log.info("Проверка названия успешна: товар соответствует поисковому запросу.");

        Allure.step("Математическая проверка вхождения цены товара в заданный лимит");
        double actualPrice = searchPage.getPriceOfFirstSearchResult();
        Assertions.assertTrue(actualPrice >= priceFrom && actualPrice <= priceTo,
                String.format("Цена товара %.2f руб. вышла за рамки фильтра [%.2f - %.2f] руб.", actualPrice, priceFrom, priceTo));
        log.info("Проверка цены успешна: цена %.2f руб. входит в заданный диапазон.", actualPrice);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Форма расширенного поиска")
    @Description("Проверка работоспособности кнопки 'Сбросить': все заполненные инпуты должны возвращаться в исходное пустое состояние")
    @DisplayName("Расширенный поиск: сброс всех фильтров")
    public void clearExpandSearch() {
        log.info("=== СТАРТ ТЕСТА: Проверка сброса фильтров расширенного поиска ===");

        Allure.step("Открытие и заполнение полей расширенного поиска тестовыми данными");
        searchPage.open();
        searchPage.clickButtonSearch();
        searchPage.setTextToKeyWordsInput("Пылесос");
        searchPage.setPriceFromToInput("200", "800");
        log.info("Форма фильтров заполнена тестовыми данными.");

        Allure.step("Нажатие кнопки сброса фильтров (Clear)");
        searchPage.clickButtonClear();
        log.info("Нажата кнопка 'Сбросить'. Проверяем очистку полей по шаблону именования...");

        Allure.step("Проверка очистки всех инпутов формы");
        Assertions.assertEquals("", searchPage.getTextFromInputKeyWords(), "Поле ключевых слов не очистилось!");
        Assertions.assertEquals("", searchPage.getTextFromInputPriceFrom(), "Поле цены 'От' не очистилось!");
        Assertions.assertEquals("", searchPage.getTextFromInputPriceTo(), "Поле цены 'До' не очистилось!");
        log.info("Тест успешно пройден: форма полностью очищена.");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Безопасность системы (Security Testing)")
    @Description("Тест на уязвимость SQL injection (Tautology) по классификации OWASP Top 10. Проверка того, что бэкенд экранирует кавычки и выдает штатную страницу 404, вместо системной ошибки базы данных")
    @DisplayName("Поиск: защита от SQL-инъекций")
    public void secutityCheckSimpleSearch() {
        String sqlPayload = "смартфон' OR 1=1 --";

        log.info("=== СТАРТ ТЕСТА БЕЗОПАСНОСТИ: Отправка SQL-инъекции: [{}] ===", sqlPayload);

        Allure.step("Отправка SQL-инъекции в строку поиска: " + sqlPayload);
        searchPage.open();
        searchPage.findPurchaseWithoutFilter(sqlPayload);

        Allure.step("Считывание сообщения системы и проверка корректной обработки (отсутствие ошибки 500)");
        String actualMessage = searchPage.getProductNotFoundMessage();
        Assertions.assertTrue(actualMessage.contains("не найдено ни одного товара"),
                String.format("Сайт выдал нетипичную системную ошибку или упал! Текст на UI: '%s'", actualMessage));

        log.info("Тест безопасности пройден на отлично: параметризация запросов на mx.by работает корректно.");
    }
}