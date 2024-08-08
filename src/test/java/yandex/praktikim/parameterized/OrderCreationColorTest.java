package yandex.praktikim.parameterized;

import com.example.model.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.praktikim.BaseOrderCreationTestClass;

/**
 * Тут проверяем условия:
 * можно указать один из цветов — BLACK или GREY;
 * можно указать оба цвета;
 * можно совсем не указывать цвет;
 */
@RunWith(Parameterized.class)
public class OrderCreationColorTest extends BaseOrderCreationTestClass {
    private final String[] colors;
    private final Integer expectedStatusCode;

    public OrderCreationColorTest(String[] colors, Integer expectedStatusCode) {
        this.colors = colors;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {new String[] {"BLACK"}, 201},
                {new String[] {"GREY"}, 201},
                {new String[] {"BLACK", "GREY"}, 201},
                {null, 201},
        };
    }

    //Почему тут нет удаления тестовых данных - пояснил в OrderCreationTest
    @Test
    @DisplayName("Создаем заказ с выбранным(и) цветом(ами) через статус код ответа")
    @Description("Проверяем все указанные в задании проверки, тест корректности статус кода и тела ответа отдельно" +
            "в OrderCreationTest. Видимо так и было задумано, так как в реализации все поля необязательные")
    public void createOrderWithSelectColor() {
        Order order = initCustomColorOrderJavaObject(colors);
        Response response = sendPostRequestOrder(order);
        compareStatusCode(response, expectedStatusCode);
    }
}
