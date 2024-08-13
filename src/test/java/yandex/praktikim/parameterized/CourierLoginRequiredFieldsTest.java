package yandex.praktikim.parameterized;

import api.client.CourierClient;
import com.example.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//Создал отельный класс для проверок обязательности полей и сделал его параметризованным
@RunWith(Parameterized.class)
public class CourierLoginRequiredFieldsTest {
    private final String login;
    private final String password;

    private final Courier courier;
    private final CourierClient courierClient;

    public CourierLoginRequiredFieldsTest(
            String login,
            String password) {
        this.login = login;
        this.password = password;
        courierClient = new CourierClient();
        courier = new Courier("AaaAAA", "BbbBBB", "CccCС");
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"AaaAAA", null},
                {null, "BbbBBB"},
                {null, null},
        };
    }

    //нужно при таймауте логина
    @After
    public void removeCourier() {
        courierClient.removeCourier(courier);
    }

    //Периодически сервер таймаутит, не знаю с чем это связано
    @Test
    @DisplayName("Проверка на обязательность (всех) полей в теле запроса + проверка на корректный 400 статус код")
    @Description("Проверка всех частных случаев, когда не хватает каких-то полей" +
            "По документации ВСЕ поля должны быть, иначе ошибка")
    public void checkRequiredFieldsLoginCourierRequestError() {
        courierClient.courierCreate(courier, 201);
        courierClient.courierLogin(new Courier(login, password), 400);
    }
}
