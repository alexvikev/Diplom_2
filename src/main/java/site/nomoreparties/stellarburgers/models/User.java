package site.nomoreparties.stellarburgers.models;

import lombok.Data;

//класс содержит данные пользователя
//для удобства импользована библиотека lombok
@Data
public class User {

    private String email;
    private String password;
    private String name;
    private String accessToken;

}
