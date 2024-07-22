package site.nomoreparties.stellarburgers.models;

import lombok.Data;

import java.util.List;

//Класс содержит данные заказа
@Data
public class Order {

    private List<String> ingredients;
}
