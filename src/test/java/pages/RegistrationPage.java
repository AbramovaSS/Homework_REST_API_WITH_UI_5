package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {

    private final SelenideElement
            usernameInput = $("#username"),
            passwordInput = $("#password"),
            confirmPasswordInput = $("#confirm-password"),
            signupButton = $("[data-testid=signup-button]");

    @Step("Перейти на страницу регистрации")
    public RegistrationPage openPage() {
        open("/signup");
        return this;
    }

    @Step("Ввести логин \"{value}\" в поле \"Логин\"")
    public RegistrationPage setUsername(String value) {
        usernameInput.setValue(value);
        return this;
    }

    @Step("Ввести пароль \"{value}\" в поле \"Пароль\"")
    public RegistrationPage setPassword(String value) {
        passwordInput.setValue(value);
        return this;
    }

    @Step("Повторить пароль \"{value}\" в поле \"Повтор пароля\"")
    public RegistrationPage setConfirmPassword(String value) {
        confirmPasswordInput.pressEnter();
        return this;
    }

    @Step("Проверить, что кнопка \"Зарегистрироваться\" скрылась")
    public RegistrationPage signupButton() {
        signupButton.should(visible);
        return this;
    }

}
