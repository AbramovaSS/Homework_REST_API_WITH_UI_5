package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement
            usernameInput = $("[data-testid=username-input]"),
            passwordInput = $("[data-testid=password-input]"),
            submitButton = $("[data-testid=submit-button]");

    @Step("Перейти на страницу авторизации")
    public LoginPage openPage() {
        open("/signin");
        return this;
    }

    @Step("Ввести логин \"{value}\" в поле \"Логин\"")
    public LoginPage setUsername(String value) {
        usernameInput.setValue(value);
        return this;
    }

    @Step("Ввести пароль \"{value}\" в поле \"Пароль\"")
    public LoginPage setPassword(String value) {
        passwordInput.setValue(value).pressEnter();
        return this;
    }

    @Step("Проверить, что кнопка \"Войти\" скрылась")
    public LoginPage submit() {
        submitButton.should(disappear);
        return this;
    }

}
