package tests.api;

import data.TestData;
import models.login.FieldRequiredResponseModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

public class UpdateUserTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("[API] Успешное обновление данных пользователя методом PUT")
    public void successfulUpdateUserDataTest() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        String access = "Bearer " + loginResponse.access();

        api.auth.userAuthorization(new LoginBodyModel(testData.username, testData.password));

        SuccessfulUpdateResponseModel updateResponse = api.user.userDataUpdate(new UpdateBodyModel(
                testData.newUsername,
                testData.newFirstName,
                testData.newLastName,
                testData.newEmail),
                access);

        step("Проверка корректности обновленных данных пользователя", () -> {
            Assertions.assertThat(updateResponse.id()).isEqualTo(registrationResponse.id());
            Assertions.assertThat(updateResponse.username()).isEqualTo(testData.newUsername);
            Assertions.assertThat(updateResponse.firstName()).isEqualTo(testData.newFirstName);
            Assertions.assertThat(updateResponse.lastName()).isEqualTo(testData.newLastName);
            Assertions.assertThat(updateResponse.email()).isEqualTo(testData.newEmail);
            assertThat(updateResponse.remoteAddr()).isEqualTo(registrationResponse.remoteAddr());
        });
    }

    @Test
    @DisplayName("[API] Успешное добавление email методом PATCH")
    public void successfulEmailUpdateTest() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(
                        testData.username,
                        testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        String access = "Bearer " + loginResponse.access();

        api.auth.userAuthorization(new LoginBodyModel(testData.username, testData.password));

        SuccessfulUpdateResponseModel updateResponse = api.user.userEmailUpdate
                (new UpdateEmailBodyModel(
                        testData.newEmail),
                        access);

        step("Проверка корректности обновленных данных, включая email", () -> {
            Assertions.assertThat(updateResponse.id()).isEqualTo(registrationResponse.id());
            Assertions.assertThat(updateResponse.username()).isEqualTo(testData.username);
            Assertions.assertThat(updateResponse.firstName()).isEqualTo("");
            Assertions.assertThat(updateResponse.lastName()).isEqualTo("");
            Assertions.assertThat(updateResponse.email()).isEqualTo(testData.email);
            assertThat(updateResponse.remoteAddr()).isEqualTo(registrationResponse.remoteAddr());
        });
    }

    @Test
    @DisplayName("[API] Обработка ошибки при передаче пустого поля username")
    public void usernameFieldRequiredWhenUpdatingTest() {
        api.user.userRegistration(new RegistrationBodyModel(
                testData.username,
                testData.password));

        SuccessfulLoginResponseModel loginResponse = api.auth.userAuthorization
                (new LoginBodyModel(
                        testData.username,
                        testData.password));

        String access = "Bearer " + loginResponse.access();

        api.auth.userAuthorization(new LoginBodyModel(testData.username, testData.password));

        FieldRequiredResponseModel updateWithoutUsernameResponse = api.user.emptyUsernameUpdate
                (new UpdateWithoutUsernameBodyModel(
                        testData.newFirstName,
                        testData.newLastName,
                        testData.newEmail),
                        access);

        step("Проверка, что API вернул ошибку о незаполненном поле username", () -> {
            String actualUsernameError = updateWithoutUsernameResponse.username().get(0);
            Assertions.assertThat(actualUsernameError).isEqualTo(FIELD_IS_REQUIRED);
        });
    }
}
