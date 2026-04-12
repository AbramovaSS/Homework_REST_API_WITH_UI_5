package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    private final SelenideElement
            signupLink = $("[data-testid=signup-link]"),
            filterOption = $(".filter-options").parent().parent().$(".filter-option");

    private String clubLinkSelector(int clubId) {
        return String.format("a[href*='/clubs/%d']", clubId);
    }
    public void openClubById(int clubId) {
        Selenide.$(clubLinkSelector(clubId)).click();
    }


    @Step("Открыть главную страницу")
    public BasePage openPage() {
        open("/");
        return this;
    }

    @Step("Нажать на кнопку \"Регистрация\" в хедере")
    public BasePage signupLinkButton() {
        signupLink.pressEnter();
        return this;
    }

    @Step("Нажать на кнопку \"Мои клубы\"")
    public BasePage filterOptionButton() {
        filterOption.click();
        return this;
    }

}
