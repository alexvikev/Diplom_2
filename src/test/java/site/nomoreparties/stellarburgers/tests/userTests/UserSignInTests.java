package site.nomoreparties.stellarburgers.tests.userTests;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.models.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;
import static org.hamcrest.Matchers.is;

//Класс содержит тесты авторизации пользователя
public class UserSignInTests {
    private User user;
    private UserSteps userSteps;

    @Before
    public void setUp(){
        //RestAssured.filters(new RequestLoggingFilter());

        user = new User();
        userSteps = new UserSteps();

        userSteps.generateUserData(user);
        userSteps.createUser(user);
    }

    @DisplayName("Успешная авторизация пользователя")
    @Test
    public void userSignInReturnTrueTest(){
        userSteps
                .userSignIn(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @DisplayName("Авторизация с некорректным email")
    @Test
    public void userSignInWithInvalidEmailReturnFalseTest(){
        String originEmail = user.getEmail();

        userSteps.changeUserEmail(user);

        userSteps
                .userSignIn(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));

        user.setEmail(originEmail);
    }

    @DisplayName("Авторизация с некорректным password")
    @Test
    public void userSignInWithInvalidPasswordReturnFalseTest(){
        String originPassword = user.getPassword();

        userSteps.changeUserPassword(user);

        userSteps
                .userSignIn(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));

        user.setPassword(originPassword);
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
