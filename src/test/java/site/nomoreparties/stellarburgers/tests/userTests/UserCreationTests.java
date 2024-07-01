package site.nomoreparties.stellarburgers.tests.userTests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.models.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;
import static org.hamcrest.Matchers.is;

//Класс содержит тесты создания пользователя
public class UserCreationTests {
    private User user;
    private UserSteps userSteps;

    @Before
    public void setUp(){
        user = new User();
        userSteps = new UserSteps();

        userSteps.generateUserData(user);
    }

    @DisplayName("Пользователя можно создать")
    @Test
    public void userCreationTestReturnTrueTest(){
        userSteps
                .createUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @DisplayName("Нельзя создать двух одинаковых пользователей")
    @Test
    public void userDuplicateReturnFalseTest(){
        userSteps
                .createUser(user);

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("User already exists"));
    }

    @DisplayName("Нельзя создать пользователя без email")
    @Test
    public void userCreationWithoutEmailTest(){
        user.setEmail(null);

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @DisplayName("Нельзя создать пользователя без поля password")
    @Test
    public void userCreationWithoutPasswordTest(){
        user.setPassword("");

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));
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
