package by.mx.ui;

import by.mx.ui.page.AuthPage;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import java.util.function.Consumer;

public enum AuthErrorValidationCase {
    BOTH(page -> {
        Assertions.assertTrue(page.isEmailFieldInvalid(), "Поле Email должно быть подсвечено ошибкой");
        Assertions.assertTrue(page.isPasswordFieldInvalid(), "Поле Пароль должно быть подсвечено ошибкой");
    }),
    EMAIL(page -> {
        Assertions.assertTrue(page.isEmailFieldInvalid(), "Поле Email должно быть подсвечено ошибкой");
        Assertions.assertFalse(page.isPasswordFieldInvalid(), "Поле Пароль НЕ должно иметь индикации ошибки");
    }),
    PASSWORD(page -> {
        Assertions.assertTrue(page.isPasswordFieldInvalid(), "Поле Пароль должно быть подсвечено ошибкой");
        Assertions.assertFalse(page.isEmailFieldInvalid(), "Поле Email НЕ должно иметь индикации ошибки");
    });

    private final Consumer<AuthPage> assertionLogic;

    AuthErrorValidationCase(Consumer<AuthPage> assertionLogic) {
        this.assertionLogic = assertionLogic;
    }

    @Step("Выполнить визуальную валидацию для кейса: {this}")
    public void verify(AuthPage authPage) {
        this.assertionLogic.accept(authPage);
    }
}