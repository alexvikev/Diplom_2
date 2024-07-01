package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import site.nomoreparties.stellarburgers.models.User;
import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.urls.EndPoints.*;
import static site.nomoreparties.stellarburgers.urls.TestStands.RC_STAND_URL;

//класс содержит степы для тестирования функционала пользователя
public class UserSteps {

    @Step("Генерация данных пользователя для тестирования")
    public void generateUserData(User user){
        user.setEmail((RandomStringUtils.randomAlphabetic(7)
                .toLowerCase()) + "@gmail.com");
        user.setPassword(RandomStringUtils.randomAlphabetic(10));
        user.setName(RandomStringUtils.randomAlphabetic(10));

    }

    @Step("Изменить email пользователя")
    public void changeUserEmail(User user){
        user.setEmail((RandomStringUtils.randomAlphabetic(6)
                .toLowerCase()) + "@mail.ru");
    }

    @Step("Изменить пароль пользователя")
    public void changeUserPassword(User user){
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
    }

    @Step("Изменить имя пользователя")
    public void changeUserName(User user){
        user.setName(RandomStringUtils.randomAlphabetic(8));
    }

    @Step("Содать нового пользователя")
    public ValidatableResponse createUser(User user){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .body(user)
                .when()
                .post(CREATE_USER_URL)
                .then();
    }

    @Step("Авторизовать пользователя")
    public ValidatableResponse userSignIn(User user){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .body(user)
                .when()
                .post(USER_LOGIN_URL)
                .then();
    }

    @Step("Изменить данные авторизованного пользователя")
    public ValidatableResponse userWithAuthChangeData(User user){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(RC_STAND_URL)
                .body(user)
                .when()
                .patch(USER_DATA_URL)
                .then();
    }

    @Step("Изменить данные пользователя без авторизации")
    public ValidatableResponse userWithoutAuthChangeData(User user){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .body(user)
                .when()
                .patch(USER_DATA_URL)
                .then();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse deleteUser(User user){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(RC_STAND_URL)
                .when()
                .delete(USER_DATA_URL)
                .then();
    }
}
