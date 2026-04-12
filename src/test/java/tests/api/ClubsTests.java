package tests.api;

import data.TestData;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

public class ClubsTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("[API] Создание клуба")
    public void successfulCreationOfClub() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(
                clubsData,
                access);

        step("Проверка данных созданного клуба", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(createClubResponse.id()).isNotNull();
                softAssertions.assertThat(createClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
                softAssertions.assertThat(createClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
                softAssertions.assertThat(createClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
                softAssertions.assertThat(createClubResponse.description()).isEqualTo(testData.description);
                softAssertions.assertThat(createClubResponse.telegramChatLink()).isEqualTo(TELEGRAM_CHAT_LINK);
                softAssertions.assertThat(createClubResponse.owner()).isNotNull();
                softAssertions.assertThat(createClubResponse.owner()).isEqualTo(registrationResponse.id());
                softAssertions.assertThat(createClubResponse.members()).isNotNull();
                softAssertions.assertThat(createClubResponse.members()).isNotEmpty();
                softAssertions.assertThat(createClubResponse.reviews()).isEmpty();
                softAssertions.assertThat(createClubResponse.created()).isNotNull();
                softAssertions.assertThat(createClubResponse.modified()).isNull();
            });
        });
    }

    @Test
    @DisplayName("[API] Просмотр клуба")
    public void successfulViewingClub() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(clubsData, access);

        SuccessfulCreateClubResponseModel viewingClubResponse = api.clubs.clubViewing(createClubResponse.id());

        step("Проверка данных клуба", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(viewingClubResponse.id()).isEqualTo(createClubResponse.id());
                softAssertions.assertThat(viewingClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
                softAssertions.assertThat(viewingClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
                softAssertions.assertThat(viewingClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
                softAssertions.assertThat(viewingClubResponse.description()).isEqualTo(testData.description);
                softAssertions.assertThat(viewingClubResponse.telegramChatLink()).isEqualTo(TELEGRAM_CHAT_LINK);
            });
        });
    }

    @Test
    @DisplayName("[API] Редактирование телеграм-ссылки клуба")
    public void successfulEditingClub() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(
                clubsData,
                access);

        CreateClubsBodyModel clubsNewData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                NEW_TELEGRAM_CHAT_LINK);

        SuccessfulCreateClubResponseModel editingClubResponse = api.clubs.clubTelegramEditing(
                createClubResponse.id(),
                clubsNewData,
                access);

        step("Проверка данных клуба после редактирования Telegram-ссылки", () -> {
            assertThat(editingClubResponse.id()).isNotNull();
            assertThat(editingClubResponse.id()).isEqualTo(createClubResponse.id());
            assertThat(editingClubResponse.bookTitle()).isEqualTo(testData.bookTitle);
            assertThat(editingClubResponse.bookAuthors()).isEqualTo(testData.bookAuthors);
            assertThat(editingClubResponse.publicationYear()).isEqualTo(testData.publicationYear);
            assertThat(editingClubResponse.description()).isEqualTo(testData.description);
            assertThat(editingClubResponse.telegramChatLink()).isEqualTo(NEW_TELEGRAM_CHAT_LINK);
            assertThat(editingClubResponse.modified()).isNotNull();
        });
    }

    @Test
    @DisplayName("[API] Удаление клуба")
    public void successfulRemoveClub() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(
                testData.bookTitle,
                testData.bookAuthors,
                testData.publicationYear,
                testData.description,
                TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(
                clubsData,
                access);

        api.clubs.clubDeletion(createClubResponse.id(), access);

        api.clubs.clubViewing(createClubResponse.id(), access, 404);
    }
}
