package site.nomoreparties.stellarburgers.tests.orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.models.Order;
import site.nomoreparties.stellarburgers.models.User;
import site.nomoreparties.stellarburgers.steps.IngredientSteps;
import site.nomoreparties.stellarburgers.steps.OrderSteps;
import site.nomoreparties.stellarburgers.steps.UserSteps;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

//Класс содержит тесты создания заказа
public class OrdersCreationTests {
    private User user;
    private Order order;
    private UserSteps userSteps;
    private OrderSteps orderSteps;
    private IngredientSteps ingredientSteps;

    @Before
    public void setUp(){
        //RestAssured.filters(new RequestLoggingFilter());

        user = new User();
        order = new Order();

        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        ingredientSteps = new IngredientSteps();
    }

    @DisplayName("Создание заказа с ингредиентами авторизованным пользователем")
    @Test
    public void orderWithIngredientsByAuthUserReturnTrueTest(){
        userSteps.generateUserData(user);
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.setUserAccessToken(user);

        ingredientSteps.setListOfIngredients(order);

        orderSteps
                .createOrderWithAuth(order, user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("name", notNullValue());
    }

    //Тут есть дефект: в ответ приходит статус код 200
    @DisplayName("Создание заказа с ингредиентами пользователем без авторизации")
    @Test
    public void orderWithIngredientsWithoutAuthTest(){
        ingredientSteps.setListOfIngredients(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorized"));
    }

    @DisplayName("Создание заказа без ингредиентов авторизованным пользователем")
    @Test
    public void orderWithoutIngredientByAuthUserReturnTrueTest(){
        userSteps.generateUserData(user);
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.setUserAccessToken(user);

        ingredientSteps.setEmptyListOfIngredients(order);

        orderSteps
                .createOrderWithAuth(order, user)
                .statusCode(400)
                .body("success", is(false))
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }

    /* Поскольку неавторизованным пользователем можно создать заказ,
    тут тоже дфект: приходит ответ как для заказа без ингредиентов */
    @DisplayName("Создание заказа без ингредиентов пользователем без авторизации")
    @Test
    public void orderWithoutIngredientsAndWithoutAuthTest(){
        ingredientSteps.setEmptyListOfIngredients(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorized"));
    }

    @DisplayName("Создание заказа с невалидным хешем ингредиентов авторизованным пользователем")
    @Test
    public void orderWithInvalidIngredientHashByAuthUserTest(){
        userSteps.generateUserData(user);
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.setUserAccessToken(user);

        ingredientSteps.setInvalidListOfIngredients(order);

        orderSteps
                .createOrderWithAuth(order, user)
                .statusCode(500);
    }

    /* Аналогично: в ответ приходит статус код 500, как у
    авторизованого пользователя */
    @DisplayName("Создание заказа с невалидным хешем ингредиентов пользователем без авторизации")
    @Test
    public void orderWithInvalidIngredientHashWithoutAuth(){
        ingredientSteps.setInvalidListOfIngredients(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorized"));
    }

    @After
    public void tearDown(){
        userSteps.setUserAccessToken(user);

        if (user.getAccessToken() != null){
            userSteps.deleteUser(user);
        } else {
            System.out.println("Токен null");
        }
    }
}
