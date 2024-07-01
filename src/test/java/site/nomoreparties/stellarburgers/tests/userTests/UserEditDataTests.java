package site.nomoreparties.stellarburgers.tests.userTests;

import io.qameta.allure.junit4.DisplayName;
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
        user = new User();
        userSteps = new UserSteps();

        userSteps.generateUserData(user);
    }

    @DisplayName("Изменение email авторизованного пользователя")
    @Test
    public void userChangeEmailWithAuthReturnTrueTest(){
        userSteps.createUser(user);
        userSteps.userSignIn(user);
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
        userSteps.createUser(user);
        userSteps.userSignIn(user);
        userSteps.changeUserPassword(user);

        userSteps
                .userWithAuthChangeData(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("user.password", is(user.getPassword()));
    }

    @DisplayName("Изменение имени авторизованного пользователя")
    @Test
    public void userChangeNameWithAuthReturnTrueTest(){
        userSteps.createUser(user);
        userSteps.userSignIn(user);
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
        userSteps.createUser(user);
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
        userSteps.createUser(user);
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
        userSteps.createUser(user);
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
        String accessToken = userSteps.userSignIn(user)
                .extract().body().path("accessToken");

        if (accessToken != null){
            user.setAccessToken(accessToken);
            userSteps.deleteUser(user);
        }
    }
}
