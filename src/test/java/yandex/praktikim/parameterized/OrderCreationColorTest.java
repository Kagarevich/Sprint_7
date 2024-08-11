package yandex.praktikim.parameterized;

import api.client.OrdersClient;
import com.example.model.generator.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Тут проверяем условия:
 * можно указать один из цветов — BLACK или GREY;
 * можно указать оба цвета;
 * можно совсем не указывать цвет;
 */
@RunWith(Parameterized.class)
public class OrderCreationColorTest {
    private final String[] colors;

    private OrdersClient ordersClient;

    public OrderCreationColorTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {new String[] {"BLACK"}},
                {new String[] {"GREY"}},
                {new String[] {"BLACK", "GREY"}},
                {null},
        };
    }

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    //Почему тут нет удаления тестовых данных - пояснил в OrderCreationTest
    @Test
    @DisplayName("Создаем заказ с выбранным(и) цветом(ами) через статус код ответа")
    @Description("Проверяем все указанные в задании проверки, тест корректности статус кода и тела ответа отдельно" +
            "в OrderCreationTest. Видимо так и было задумано, так как в реализации все поля необязательные")
    public void createOrderWithSelectColor() {
        ordersClient.orderCreate(OrderGenerator.create(colors), 201);
    }
}
