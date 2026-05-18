package by.mx;

import by.mx.ui.SearchPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchTest extends BaseTest{
    private SearchPage searchPage;

    @Test
    @DisplayName("поиск с валидным значением")
    public void simpleSearchWithValidData(){

    }

    @Test
    @DisplayName("поиск с невалидным значением")
    public void simpleSearchWithInvalidData(){}

    @Test
    @DisplayName("нажатие на кнопку поиск -> расширенный поиск")
    public void openExpandSearch(){}

    @Test
    @DisplayName("Расширенный поиск: заполнить категорию + цену от/до -> Фильтрация работает")
    public void checkFiltersOfExpandSearchWithValidData(){}

    @Test
    @DisplayName("Расширенный поиск: сброс всех фильтров -> Кнопка 'Сбросить' очищает поля")
    public void clearExpandSearch(){}

    @Test
    @DisplayName("Поиск с символом % или _ в запросе -> SQL injection защита, не падает 500 ошибка")
    public void secutityCheckSimpleSearch(){}

}
