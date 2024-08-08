package yandex.praktikim;

import com.example.model.Order;
import com.example.repository.OrdersRepository;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class BaseOrderCreationTestClass extends BaseTestClass {

    @Step("создаю дефолтный объект курьера")
    public Order initCustomColorOrderJavaObject(String[] colors) {
        return new Order(
                "Имя",
                "Фамилия",
                "Адрес",
                "Станция метро",
                "89995556677",
                7,
                "2020-06-06",
                colors,
                "коммент"
        );
    }

    @Step("Отправляю запрос создания заказа и получаю ответ")
    public Response sendPostRequestOrder(Order requestBody) {
        return OrdersRepository.orderCreate(requestBody);
    }


    @Step("Проверка №4 - Тело ответа содержит track")
    public void getOrderTrackAfterOrderCreateDataMatching(Response sendPostRequestOrderResponse) {
        sendPostRequestOrderResponse.then()
                .body("$", hasKey("track"))
                .and()
                .body("size()", is(1));
    }
}
