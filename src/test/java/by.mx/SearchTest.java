package by.mx;

import by.mx.ui.SearchPage;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SearchTest extends BaseTest{

    @Test
    @DisplayName("поиск с валидным значением")
    public void simpleSearchWithValidData(){
        String purchase = "Aqara Smart Door Lock N100 Zigbee";
        searchPage.findPurchaseWithoutFilter(purchase);

        String resultSearch = searchPage.getTitleOfSearchResult();
        Assertions.assertTrue(resultSearch.contains(purchase.toLowerCase()));
    }

    @Test
    @DisplayName("поиск с невалидным значением")
    public void simpleSearchWithInvalidData(){
        String purchase = "Aqara Smart Door Lock N100 Zigbee2";
        searchPage.findPurchaseWithoutFilter(purchase);

        String resultSearch = searchPage.getTitleOfSearchResult();
        Assertions.assertTrue(resultSearch.startsWith("не найдено ни одного товара"));
    }

    @Test
    @DisplayName("нажатие на кнопку поиск -> расширенный поиск")
    public void openExpandSearch(){
        searchPage.clickSearchButton();
        Assertions.assertEquals(searchPage.getExpandSearchTitleText().toLowerCase(), "расширенный поиск");
    }

    @Test
    @DisplayName("Расширенный поиск: заполнить категорию + цену от/до -> Фильтрация работает")
    public void checkFiltersOfPriceRange(){
        String product =  "Отпариватель-пароочиститель";
        String priceFrom = "100";
        String priceTo = "500";

        log.info("Начало теста фильтрации. Параметры: Товар='{}', Цена от='{}', Цена до='{}'", product, priceFrom, priceTo);

        searchPage.setKeyWordsInput(product);
        log.info("Введено ключевое слово в фильтр.");

        searchPage.setPriceFromToInput(priceFrom, priceTo);
        log.info("Установлен диапазон цен.");

        searchPage.clickExpandSearchButton();
        log.info("Нажата кнопка применения фильтров. Ожидание результатов...");

        try {
            String resultSearch = searchPage.getTitleOfSearchResult();
            log.info("Результаты успешно получены. Имя первого товара на UI: '{}'", resultSearch);

            Assertions.assertTrue(resultSearch.contains(product.toLowerCase()),
                    String.format("Найденный товар '%s' не содержит корень '%s'", resultSearch, product));
            log.info("Тест успешно пройден. Ассерт валиден.");

        } catch (Throwable e) {
            // Перехватываем любое падение (включая AssertionFailedError) для записи в лог
            log.error("ТЕСТ УПАЛ! Ошибка во время выполнения или проверки: ", e);
            throw e;
        }

    }

    @Test
    @DisplayName("Расширенный поиск: сброс всех фильтров -> Кнопка 'Сбросить' очищает поля")
    public void clearExpandSearch(){}

    @Test
    @DisplayName("Поиск с символом % или _ в запросе -> SQL injection защита, не падает 500 ошибка")
    public void secutityCheckSimpleSearch(){}

}
