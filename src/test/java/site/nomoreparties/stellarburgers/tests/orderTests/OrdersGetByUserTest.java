package site.nomoreparties.stellarburgers.tests.orderTests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.models.User;
import site.nomoreparties.stellarburgers.steps.OrderSteps;
import site.nomoreparties.stellarburgers.steps.UserSteps;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

//Класс содержит тесты получения заказов конкретного пользователя
public class OrdersGetByUserTest {
    private User user;
    private OrderSteps orderSteps;
    private UserSteps userSteps;

    @Before
    public void setUp(){
        user = new User();
        orderSteps = new OrderSteps();
        userSteps = new UserSteps();
    }

    @DisplayName("Получить заказы авторизованного пользователя")
    @Test
    public void ordersGetByAuthUserTest(){
        userSteps.generateUserData(user);
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.setUserAccessToken(user);

        orderSteps.
                getOrdersWithAuthUser(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("total", notNullValue());
    }

    @DisplayName("Получить заказы пользователя без авторизации")
    @Test
    public void ordersGetWithoutAuthTest(){

        orderSteps.
                getOrdersWithoutAuth()
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
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
