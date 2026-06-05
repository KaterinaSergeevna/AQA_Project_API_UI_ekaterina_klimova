package by.mx.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Тестирование")
@Feature("Модуль авторизации")
@Link(name = "Интернет-магазин MX.by", url = "https://mx.by")
public class AuthTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Форма входа в личный кабинет")
    @Description("Негативный сценарий: проверка графического интерфейса системы при попытке авторизации с некорректными учетными данными. " +
            "Валидация триггера визуального выделения (индикации ошибки) полей ввода email и пароля в DOM-дереве.")
    @DisplayName("Авторизация: проверка визуальной индикации ошибок валидации формы")
    public void testAuthFormVisualValidationError() {
        authPage.open();
        authPage.clickButtonEnter();
    }
}
