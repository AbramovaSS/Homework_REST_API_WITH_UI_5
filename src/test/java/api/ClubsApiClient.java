package api;

import io.qameta.allure.Step;
import models.clubs.CreateClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestRemoveSpec;
import static specs.BaseSpec.requestSpec;
import static specs.clubs.ClubSpec.*;
import static specs.reviews.ReviewsSpec.createReviewResponseSpec;
import static specs.reviews.ReviewsSpec.deleteReviewResponseSpec;

public class ClubsApiClient {
    @Step("Отправить POST-запрос на создание клуба")
    public SuccessfulCreateClubResponseModel clubCreation(CreateClubsBodyModel clubsData, String access) {
        return given(requestSpec)
                .header("Authorization", access)
                .body(clubsData)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Отправить GET-запрос на просмотр клуба")
    public SuccessfulCreateClubResponseModel clubViewing(Integer clubId) {
        return given(requestSpec)
                .pathParam("id", clubId)
                .when()
                .get("/clubs/{id}/")
                .then()
                .spec(getSuccessfulClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Отправить PATCH-запрос на редактирование телеграм-ссылки клуба")
    public SuccessfulCreateClubResponseModel clubTelegramEditing(Integer clubId, CreateClubsBodyModel clubsNewData, String access) {
        return given(requestSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .body(clubsNewData)
                .when()
                .patch("/clubs/{id}/")
                .then()
                .spec(SuccessfulEditClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Отправить PUT-запрос на редактирование данных клуба")
    public SuccessfulCreateClubResponseModel clubDataEditing(Integer clubId, CreateClubsBodyModel clubsNewData, String access) {
        return given(requestSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .body(clubsNewData)
                .when()
                .put("/clubs/{id}/")
                .then()
                .spec(SuccessfulEditClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Отправить DELETE-запрос на удаление клуба")
    public void clubDeletion(Integer clubId, String access) {
        given(requestRemoveSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .when()
                .delete("/clubs/{id}/")
                .then()
                .spec(SuccessfulRemoveClubResponseSpec);
    }

    @Step("Проверить, что клуб удален")
    public void clubViewing(Integer clubId, String access, Integer expectedStatusCode) {
        given(requestRemoveSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .when()
                .get("/clubs/{id}/")
                .then()
                .statusCode(expectedStatusCode);
    }

    @Step("Отправить POST-запрос на вступление в клуб")
    public void entryIntoClub(int clubId, String access) {
        given(requestSpec)
                .pathParam("id", clubId)
                .header("Authorization", access)
                .when()
                .post("/clubs/{id}/members/me/")
                .then()
                .spec(SuccessfulRemoveClubResponseSpec)
                .extract();
    }
}
