package tests.api;

import data.TestData;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.clubs.SuccessfulCreateReviewResponseModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.reviews.CreateReviewBodyModel;
import models.reviews.EditAssessmentReviewBodyModel;
import models.reviews.NotPermissionResponseModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

public class ReviewTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("[API] Роль \"Админ клуба\": Создание отзыва")
    public void successfulCreationOfReviewAsClubOwner() {
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

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        step("Проверка данных оставленного отзыва", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(createReviewResponse.club()).isEqualTo(createClubResponse.id());
                softAssertions.assertThat(createReviewResponse.review()).isEqualTo(testData.review);
                softAssertions.assertThat(createReviewResponse.assessment()).isEqualTo(testData.assessment);
                softAssertions.assertThat(createReviewResponse.readPages()).isEqualTo(testData.readPages);
                softAssertions.assertThat(createReviewResponse.user()).isNotNull();
                softAssertions.assertThat(createReviewResponse.created()).isNotNull();
                softAssertions.assertThat(createReviewResponse.modified()).isNull();
            });
        });

    }

    @Test
    @DisplayName("[API] Редактирование отзыва")
    public void successfulEditReview() {
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

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        CreateReviewBodyModel editReviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.newReview,
                testData.newAssessment,
                testData.newReadPages);

        SuccessfulCreateReviewResponseModel editReviewResponse = api.reviews.reviewEdit(createReviewResponse.id(), access, editReviewData);

        step("Проверка данных отредактированного отзыва", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(editReviewResponse.club()).isEqualTo(createClubResponse.id());
                softAssertions.assertThat(editReviewResponse.review()).isEqualTo(testData.newReview);
                softAssertions.assertThat(editReviewResponse.assessment()).isEqualTo(testData.newAssessment);
                softAssertions.assertThat(editReviewResponse.readPages()).isEqualTo(testData.newReadPages);
                softAssertions.assertThat(editReviewResponse.user()).isNotNull();
                softAssertions.assertThat(editReviewResponse.created()).isNotNull();
                softAssertions.assertThat(editReviewResponse.modified()).isNotNull();
            });
        });
    }

    @Test
    @DisplayName("[API] Редактирование оценки в отзыве")
    public void successfulEditOfReviewAssessment() {
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

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        EditAssessmentReviewBodyModel editAssessmentReviewData = new EditAssessmentReviewBodyModel(
                testData.newAssessment);

        SuccessfulCreateReviewResponseModel editReviewResponse = api.reviews.reviewAssessmentEdit(
                createReviewResponse.id(),
                access,
                editAssessmentReviewData);

        step("Проверка данных после обновления оценки в отзыве", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(editAssessmentReviewData.assessment()).isEqualTo(testData.newAssessment);
                softAssertions.assertThat(editReviewResponse.user()).isNotNull();
                softAssertions.assertThat(editReviewResponse.created()).isNotNull();
                softAssertions.assertThat(editReviewResponse.modified()).isNotNull();
            });
        });
    }

    @Test
    @DisplayName("[API] Удаление отзыва")
    public void successfulDeletingReview() {
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

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                createClubResponse.id(),
                testData.review,
                testData.assessment,
                testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(
                access,
                reviewData);

        Integer reviewId = createReviewResponse.id();

        api.reviews.reviewDeletion(reviewId, access);

        api.reviews.reviewViewing(reviewId, access, 404);
    }

    @Test
    @DisplayName("[API] Редактирование чужого отзыва")
    public void EditSomeoneElseReview() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        String access = "Bearer " + loginResponse.access();

        CreateReviewBodyModel editReviewData = new CreateReviewBodyModel(
                CLUB_ID_WITH_REVIEWS,
                testData.review,
                testData.assessment,
                testData.readPages);

        NotPermissionResponseModel editReviewNotPermissionResponse = api.reviews.notPermissionResponse(
                REVIEW_ID,
                access,
                editReviewData);

        step("Проверка сообщения об ошибке при попытке изменить чужой отзыв", () -> {
            String actualDetail = editReviewNotPermissionResponse.detail();
            assertThat(actualDetail).isEqualTo(EDIT_SOMEONE_ELSE_REVIEW_ERROR);
        });
    }

    @Test
    @DisplayName("[API] Удаление чужого отзыва")
    public void DeleteSomeoneElseReview() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        String access = "Bearer " + loginResponse.access();

        NotPermissionResponseModel deleteReviewNotPermissionResponse = api.reviews.notPermissionDeleteResponse
                (REVIEW_ID, access);

        step("Проверка сообщения об ошибке при попытке удалить чужой отзыв", () -> {
            String actualDetail = deleteReviewNotPermissionResponse.detail();
            assertThat(actualDetail).isEqualTo(EDIT_SOMEONE_ELSE_REVIEW_ERROR);
        });

    }

    @Test
    @DisplayName("[API] Роль \"Участник клуба\": Создание отзыва")
    public void successfulCreationOfReviewAsClubParticipant() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel(
                testData.username,
                testData.password));

        String access = "Bearer " + loginResponse.access();

        api.clubs.entryIntoClub(ClUB_ID, access);

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel(
                ClUB_ID,
                testData.review,
                testData.assessment,
                testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(
                access,
                reviewData);

        step("Проверка данных оставленного отзыва", () -> {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(createReviewResponse.club()).isEqualTo(ClUB_ID);
                softAssertions.assertThat(createReviewResponse.review()).isEqualTo(testData.review);
                softAssertions.assertThat(createReviewResponse.assessment()).isEqualTo(testData.assessment);
                softAssertions.assertThat(createReviewResponse.readPages()).isEqualTo(testData.readPages);
                softAssertions.assertThat(createReviewResponse.user()).isNotNull();
                softAssertions.assertThat(createReviewResponse.created()).isNotNull();
                softAssertions.assertThat(createReviewResponse.modified()).isNull();
            });
        });
    }
}