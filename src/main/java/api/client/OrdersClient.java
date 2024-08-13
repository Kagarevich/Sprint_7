package api.client;

import com.example.model.Order;
import com.example.model.OrderList;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class OrdersClient {

    private static final String ORDERS_PATH = "/api/v1/orders";
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    @Step("Получение списка заказов")
    public Response orderListGet(int expectedStatusCode) {
        return given()
                .baseUri(BASE_URI)
                .get(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Создание заказа")
    public Response orderCreate(Order order, int expectedStatusCode) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .baseUri(BASE_URI)
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Проверка №4 - Тело ответа содержит track")
    public void orderCreateResponseDataMatch(Response sendPostRequestOrderResponse) {
        sendPostRequestOrderResponse.then()
                .body("$", hasKey("track"))
                .and()
                .body("size()", is(1));
    }

    @Step("Мэтчим данные. Можно ли тело ответа спарсить в список заказов")
    @Description("Смотрим, корректный ли ответ по содержанию." +
            "Я не смог найти подходящий мэтчер, а проверку на мэтчер необходима для проверки тела ответа." +
            "мэтчер any() не помог" +
            "Поэтому придумал такое решение.")
    public void orderListResponseDataMatch(Response response) {
        try {
            response.body().as(OrderList.class);
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.assertTrue("Не удалось спарсить данные, текст ошибки: \n" + ex.getMessage(), false);
        }
    }
}
