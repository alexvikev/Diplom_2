package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.urls.EndPoints.GET_INGREDIENT_LIST_URL;
import static site.nomoreparties.stellarburgers.urls.TestStands.RC_STAND_URL;

//Класс содержит шаги для работы с ингредиентами
public class IngredientSteps {

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredientsData(){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .when()
                .get(GET_INGREDIENT_LIST_URL)
                .then();
    }
}
