package site.nomoreparties.stellarburgers.tests.userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.models.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;
import static org.hamcrest.Matchers.is;

//Класс содержит тесты изменения данных пользователя
public class UserEditDataTests {
    private User user;
    private UserSteps userSteps;

    @Before
    public void setUp(){
//        RestAssured.filters(new RequestLoggingFilter());

        user = new User();
        userSteps = new UserSteps();

        userSteps.generateUserData(user);
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.setUserAccessToken(user);
    }

    @DisplayName("Изменение email авторизованного пользователя")
    @Test
    public void userChangeEmailWithAuthReturnTrueTest(){
        userSteps.changeUserEmail(user);

        userSteps
                .userWithAuthChangeData(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("user.email", is(user.getEmail()));
    }

    @DisplayName("Изменение пароля авторизоанного пользователя")
    @Test
    public void userChangePasswordWithAuthReturnTrueTest(){
        userSteps.changeUserPassword(user);

        userSteps
                .userWithAuthChangeData(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @DisplayName("Изменение имени авторизованного пользователя")
    @Test
    public void userChangeNameWithAuthReturnTrueTest(){
        userSteps.changeUserName(user);

        userSteps
                .userWithAuthChangeData(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("user.name", is(user.getName()));
    }

    @DisplayName("Изменение email пользователя без авторизации")
    @Test
    public void userChangeEmailWithoutAuthReturnFalseTest(){
        userSteps.changeUserEmail(user);

        userSteps
                .userWithoutAuthChangeData(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @DisplayName("Изменение пароля пользователя без авторизации")
    @Test
    public void userChangePasswordWithoutAuthReturnFalseTest(){
        userSteps.changeUserPassword(user);

        userSteps
                .userWithoutAuthChangeData(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @DisplayName("Изменение имени пользователя без авторизации")
    @Test
    public void userChangeNameWithoutAuthReturnFalseTest(){
        userSteps.changeUserName(user);

        userSteps
                .userWithoutAuthChangeData(user)
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
