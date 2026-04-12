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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

public class ReviewTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @Tag("API")
    @DisplayName("Роль \"Админ клуба\": Создание отзыва")
    public void successfulCreationOfReviewAsClubOwner() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(clubsData, access);

        Integer clubId = createClubResponse.id();

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel
                (clubId, testData.review, testData.assessment, testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        step("Проверка данных оставленного отзыва", () -> {
            assertThat(createReviewResponse.club()).isEqualTo(clubId);
            assertThat(createReviewResponse.review()).isEqualTo(testData.review);
            assertThat(createReviewResponse.assessment()).isEqualTo(testData.assessment);
            assertThat(createReviewResponse.readPages()).isEqualTo(testData.readPages);
            assertThat(createReviewResponse.user()).isNotNull();
            assertThat(createReviewResponse.created()).isNotNull();
            assertThat(createReviewResponse.modified()).isNull();
        });

    }

    @Test
    @Tag("API")
    @DisplayName("Редактирование отзыва")
    public void successfulEditReview() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(clubsData, access);

        Integer clubId = createClubResponse.id();

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel
                (clubId, testData.review, testData.assessment, testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        Integer reviewId = createReviewResponse.id();

        CreateReviewBodyModel editReviewData = new CreateReviewBodyModel
                (clubId, testData.newReview, testData.newAssessment, testData.newReadPages);

        SuccessfulCreateReviewResponseModel editReviewResponse = api.reviews.reviewEdit(reviewId, access, editReviewData);

        step("Проверка данных отредактированного отзыва", () -> {
            assertThat(editReviewResponse.club()).isEqualTo(clubId);
            assertThat(editReviewResponse.review()).isEqualTo(testData.newReview);
            assertThat(editReviewResponse.assessment()).isEqualTo(testData.newAssessment);
            assertThat(editReviewResponse.readPages()).isEqualTo(testData.newReadPages);
            assertThat(editReviewResponse.user()).isNotNull();
            assertThat(editReviewResponse.created()).isNotNull();
            assertThat(editReviewResponse.modified()).isNotNull();
        });
    }

    @Test
    @Tag("API")
    @DisplayName("Редактирование оценки в отзыве")
    public void successfulEditOfReviewAssessment() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(clubsData, access);

        Integer clubId = createClubResponse.id();

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel
                (clubId, testData.review, testData.assessment, testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        Integer reviewId = createReviewResponse.id();

        EditAssessmentReviewBodyModel editAssessmentReviewData = new EditAssessmentReviewBodyModel(testData.newAssessment);

        SuccessfulCreateReviewResponseModel editReviewResponse = api.reviews.reviewAssessmentEdit(reviewId, access, editAssessmentReviewData);

        step("Проверка данных после обновления оценки в отзыве", () -> {
            assertThat(editAssessmentReviewData.assessment()).isEqualTo(testData.newAssessment);
            assertThat(editReviewResponse.user()).isNotNull();
            assertThat(editReviewResponse.created()).isNotNull();
            assertThat(editReviewResponse.modified()).isNotNull();
        });
    }

    @Test
    @Tag("API")
    @DisplayName("Удаление отзыва")
    public void successfulDeletingReview() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        CreateClubsBodyModel clubsData = new CreateClubsBodyModel(testData.bookTitle, testData.bookAuthors,
                testData.publicationYear, testData.description, TELEGRAM_CHAT_LINK);

        String access = "Bearer " + loginResponse.access();

        SuccessfulCreateClubResponseModel createClubResponse = api.clubs.clubCreation(clubsData, access);

        Integer clubId = createClubResponse.id();

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel
                (clubId, testData.review, testData.assessment, testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        Integer reviewId = createReviewResponse.id();

        api.reviews.reviewDeletion(reviewId, access);

        api.reviews.reviewViewing(reviewId, access, 404);
    }

    @Test
    @Tag("API")
    @DisplayName("Редактирование чужого отзыва")
    public void EditSomeoneElseReview() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        String access = "Bearer " + loginResponse.access();

        CreateReviewBodyModel editReviewData = new CreateReviewBodyModel
                (CLUB_ID_WITH_REVIEWS, testData.review, testData.assessment, testData.readPages);

        NotPermissionResponseModel editReviewNotPermissionResponse = api.reviews.notPermissionResponse
                (REVIEW_ID, access, editReviewData);

        step("Проверка сообщения об ошибке при попытке изменить чужой отзыв", () -> {
            String actualDetail = editReviewNotPermissionResponse.detail();
            assertThat(actualDetail).isEqualTo(EDIT_SOMEONE_ELSE_REVIEW_ERROR);
        });
    }

    @Test
    @Tag("API")
    @DisplayName("Удаление чужого отзыва")
    public void DeleteSomeoneElseReview() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        String access = "Bearer " + loginResponse.access();

        NotPermissionResponseModel deleteReviewNotPermissionResponse = api.reviews.notPermissionDeleteResponse(REVIEW_ID, access);

        step("Проверка сообщения об ошибке при попытке удалить чужой отзыв", () -> {
            String actualDetail = deleteReviewNotPermissionResponse.detail();
            assertThat(actualDetail).isEqualTo(EDIT_SOMEONE_ELSE_REVIEW_ERROR);
        });

    }

    @Test
    @Tag("API")
    @DisplayName("Роль \"Участник клуба\": Создание отзыва")
    public void successfulCreationOfReviewAsClubParticipant() {
        api.user.userRegistration(new RegistrationBodyModel(testData.username, testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization(new LoginBodyModel
                (testData.username, testData.password));

        String access = "Bearer " + loginResponse.access();

        api.clubs.entryIntoClub(testData.clubId, access);

        CreateReviewBodyModel reviewData = new CreateReviewBodyModel
                (testData.clubId, testData.review, testData.assessment, testData.readPages);

        SuccessfulCreateReviewResponseModel createReviewResponse = api.reviews.reviewCreation(access, reviewData);

        step("Проверка данных оставленного отзыва", () -> {
            assertThat(createReviewResponse.club()).isEqualTo(testData.clubId);
            assertThat(createReviewResponse.review()).isEqualTo(testData.review);
            assertThat(createReviewResponse.assessment()).isEqualTo(testData.assessment);
            assertThat(createReviewResponse.readPages()).isEqualTo(testData.readPages);
            assertThat(createReviewResponse.user()).isNotNull();
            assertThat(createReviewResponse.created()).isNotNull();
            assertThat(createReviewResponse.modified()).isNull();
        });

    }
}