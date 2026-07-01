package by.mx.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Epic("UI Тестирование")
@Feature("Модуль авторизации")
@Link(name = "Интернет-магазин MX.by", url = "https://mx.by")
public class AuthTest extends UiBaseTest {

    static Stream<Arguments> authValidationDataProvider() {
        return Stream.of(
                Arguments.of("", "", AuthErrorValidationCase.BOTH),
                Arguments.of("", "wrong_pass", AuthErrorValidationCase.EMAIL),
                Arguments.of("user@mail.ru", "", AuthErrorValidationCase.PASSWORD),
                Arguments.of(" user@mail.ru ", "pass", AuthErrorValidationCase.EMAIL)
        );
    }

    @ParameterizedTest
    @MethodSource("authValidationDataProvider")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Форма входа в личный кабинет")
    @Description("Негативный сценарий: проверка графического интерфейса системы при попытке авторизации " +
            "с некорректными учетными данными. " +
            "Валидация триггера визуального выделения (индикации ошибки) полей ввода email и пароля в DOM-дереве.")
    @DisplayName("Авторизация: проверка визуальной индикации ошибок валидации формы")

    public void testAuthFormVisualValidationError(String email, String password, AuthErrorValidationCase validationCase) {
        authPage.open()
            .clickButtonEnter()
            .setTextToInputEmail(email)
            .setTextToInputPassword(password)
            .clickButtonLogin();

        validationCase.verify(authPage);
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
