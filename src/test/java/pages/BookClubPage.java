package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.ContentClubComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BookClubPage {
    private final SelenideElement clubDetails = $(".club-details"),
            clubTitle = $(".club-header"),
            clubAuthors = $(".authors"),
            clubPublicationYear = $(".year"),
            clubDescription = $(".description"),
            clubDetail = $(".club-details");

    ContentClubComponent contentClubComponent = new ContentClubComponent();

    @Step("Открыть ресурс и передать авторизацию {value}")
    public BookClubPage openFaviconPage(String value) {
        open("/favicon.ico");
        localStorage().setItem("book_club_auth", value);
        return this;
    }

    @Step("Перейти в созданный клуб по id: {value}")
    public BookClubPage openBookClubPage(int value) {
        open("/clubs/" + value);
        return this;
    }

    @Step("Проверить, что отобразился созданный клуб")
    public BookClubPage checkClubDetails() {
        clubDetails.shouldBe(visible);
        return this;
    }

    @Step("Проверить данные")
    public BookClubPage checkResult(SelenideElement key, String value) {
        contentClubComponent.checkResultValues(key, value);
        return this;
    }

    public SelenideElement getClubTitle() {
        return clubTitle;
    }

    public SelenideElement getClubAuthors() {
        return clubAuthors;
    }

    public SelenideElement getClubPublicationYear() {
        return clubPublicationYear;
    }

    public SelenideElement getClubDescription() {
        return clubDescription;
    }

    @Step("Обновить страницу браузера")
    public BookClubPage refreshPage() {
        refresh();
        return this;
    }

    @Step("Проверить, что отобразился созданный клуб")
    public BookClubPage checkErrorClubDetail() {
        clubDetail.shouldBe(text("Не удалось загрузить информацию о клубе"));
        return this;
    }
}
