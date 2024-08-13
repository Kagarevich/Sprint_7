package yandex.praktikim.nonparameterized;

import api.client.OrdersClient;
import com.example.model.generator.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

//Тут отдельные проверки статус кода и тела ответа успешного запроса
//Не тестил тут ошибки, так как об этом в задании не было, а с курьером это указывалось в явной форме
public class OrderCreationTest {


    private OrdersClient ordersClient;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Проверка на корректный статус код при создании заказа - должен быть 201 + тело ответа track")
    @Description("Проверка на 201 статус код - не указан в списке проверок, но указан в блоке " +
            "Как будут оценивать твою работу, пункт 11")
    public void createOrderRequestIsSuccessAndResponseStatusCodeIsCreated() {
        Response response = ordersClient
                .orderCreate(
                        OrderGenerator.create(new String[] {"BLACK", "GREY"}),
                        201
                );
        ordersClient.orderCreateResponseDataMatch(response);
    }
}
