package site.nomoreparties.stellarburgers.urls;

//класс содаржит эндпоинты приложения
public class EndPoints {

    //Пользователь
    public static final String CREATE_USER_URL = "/api/auth/register";
    public static final String USER_DATA_URL = "/api/auth/user";
    public static final String USER_LOGIN_URL = "/api/auth/login";

    //Заказы
    public static final String ORDER_DATA_URL = "/api/orders";

    //Ингредиенты
    public static final String GET_INGREDIENT_LIST_URL = "/api/ingredients";

}
