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
    private String accessToken;

    @Before
    public void setUp(){
        user = new User();
        userSteps = new UserSteps();

        userSteps.generateUserData(user);
    }

    @DisplayName("Успешная авторизация пользователя")
    @Test
    public void userSignInReturnTrueTest(){
        userSteps
                .createUser(user);
        userSteps
                .userSignIn(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @DisplayName("Авторизация с некорректным email")
    @Test
    public void userSignInWithInvalidEmailReturnFalseTest(){
        userSteps
                .createUser(user);
        user
                .setEmail(RandomStringUtils.randomAlphabetic(5));
        userSteps
                .userSignIn(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }

    @DisplayName("Авторизация с некорректным password")
    @Test
    public void userSignInWithInvalidPasswordReturnFalseTest(){
        userSteps
                .createUser(user);
        user
                .setPassword(RandomStringUtils.randomAlphabetic(10));
        userSteps
                .userSignIn(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }

    @After
    public void tearDown(){

        if (user.getAccessToken() != null){
            userSteps.deleteUser(user);
        }
    }
}
