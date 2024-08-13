package yandex.praktikim.nonparameterized;

import api.client.OrdersClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

//Не тестил тут ошибки, так как об этом в задании не было, а с курьером это указывалось в явной форме
public class OrderListGettingTest {

    private OrdersClient ordersClient;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Проверка, что запрос списка заказов возвращает статус код 200 + проверка тела ответа")
    @Description("В задании не были обозначены query параметры, а в доп задании обозначены," +
            "из чего делается вывод, что тут query параметры использовать не надо.")
    public void successRequestGetOKStatusCode() {
        Response response = ordersClient.orderListGet(200);
        ordersClient.orderListResponseDataMatch(response);
    }
}
