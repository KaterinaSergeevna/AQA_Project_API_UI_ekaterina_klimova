package by.mx.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@Epic("UI Тестирование")
@Feature("Модуль авторизации")
@Link(name = "Интернет-магазин MX.by", url = "https://mx.by")
public class AuthTest extends BaseTest {

    @ParameterizedTest
    @Severity(SeverityLevel.CRITICAL)
    @Story("Форма входа в личный кабинет")
    @Description("Негативный сценарий: проверка графического интерфейса системы при попытке авторизации " +
            "с некорректными учетными данными. " +
            "Валидация триггера визуального выделения (индикации ошибки) полей ввода email и пароля в DOM-дереве.")
    @DisplayName("Авторизация: проверка визуальной индикации ошибок валидации формы")

    @CsvSource({
            "'', '', both",
            "'', 'wrong_pass', email",
            "'user@mail.ru', '', password",
            "' user@mail.ru ', 'pass', email"
    })
    public void testAuthFormVisualValidationError(String email, String password, String expectedErrorField) {
        authPage.open()
            .clickButtonEnter()
            .setTextToInputEmail(email)
            .setTextToInputPassword(password)
            .clickButtonLogin();

        switch (expectedErrorField) {
            case "both":
                Assertions.assertTrue(authPage.isEmailFieldInvalid(), "Поле Email должно быть подсвечено ошибкой");
                Assertions.assertTrue(authPage.isPasswordFieldInvalid(), "Поле Пароль должно быть подсвечено ошибкой");
                break;

            case "email":
                Assertions.assertTrue(authPage.isEmailFieldInvalid(), "Поле Email должно быть подсвечено ошибкой");
                Assertions.assertFalse(authPage.isPasswordFieldInvalid(), "Поле Пароль НЕ должно иметь индикации ошибки");
                break;

            case "password":
                Assertions.assertTrue(authPage.isPasswordFieldInvalid(), "Поле Пароль должно быть подсвечено ошибкой");
                Assertions.assertFalse(authPage.isEmailFieldInvalid(), "Поле Email НЕ должно иметь индикации ошибки");
                break;

            default:
                throw new IllegalArgumentException("Неизвестный тип ожидаемой ошибки: " + expectedErrorField);
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Форма входа в личный кабинет")
    @Description("Позитивный сценарий: отображение поля ввода email при нажатии ссылки 'восстановить пароль'")
    @DisplayName("Восстановление пароля: проверка отображения окна восстановления пароля")
    public void testRestoreWindow(){
        authPage.open()
                .clickButtonEnter()
                .clickLinkRestorePassword();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Личный кабинет", authPage.getTitleRecoveryAccountText(), "Заголовок отличается"),
                () -> Assertions.assertEquals("Ваш email*", authPage.getLabelYourEmailText(), "Заголовок поля email отличается"),
                () -> Assertions.assertTrue(authPage.isInputEmailForRecoveryPasswordPresent(), "Поле ввода email отсутствует")
        );
    }
}
