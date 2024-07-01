package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.models.Order;
import site.nomoreparties.stellarburgers.models.User;
import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.urls.EndPoints.ORDER_DATA_URL;
import static site.nomoreparties.stellarburgers.urls.TestStands.RC_STAND_URL;

//Класс содержит степы для тестирования функционала заказов
public class OrderSteps {

    @Step("Создать заказ авторизованным пользователем")
    public ValidatableResponse createOrderWithAuth(Order order, User user){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(RC_STAND_URL)
                .body(order)
                .when()
                .post(ORDER_DATA_URL)
                .then();
    }

    @Step("Создать заказ без авторизации пользователя")
    public ValidatableResponse createOrderWithoutAuth(Order order){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .body(order)
                .when()
                .post(ORDER_DATA_URL)
                .then();
    }

    @Step("Получить заказы пользователя с авторизацией")
    public ValidatableResponse getOrdersWithAuthUser(User user){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(RC_STAND_URL)
                .when()
                .get(ORDER_DATA_URL)
                .then();
    }

    @Step("Получить заказы пользователя без авторизации")
    public ValidatableResponse getOrdersWithoutAuth(){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .when()
                .get(ORDER_DATA_URL)
                .then();
    }

}
