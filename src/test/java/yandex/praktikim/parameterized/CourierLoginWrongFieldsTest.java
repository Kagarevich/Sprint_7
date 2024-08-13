package yandex.praktikim.parameterized;

import api.client.CourierClient;
import com.example.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierLoginWrongFieldsTest {
    private final String login;
    private final String password;

    private final CourierClient courierClient;
    private final Courier courier;

    public CourierLoginWrongFieldsTest(
            String login,
            String password) {
        this.login = login;
        this.password = password;
        courierClient = new CourierClient();
        courier = new Courier("AaaAAAA", "BbbBBBB", "CccC");
    }

    //Проверка, если неправильный логин или пароль. Решил сделать отдельной проверкой, хотя всё то же самое, что и для
    //CourierLoginRequiredFieldsTest. По заданию это разные проверки

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"AaaAAAA", "fasdfdfagdfgsdf"},
                {"skdfjskdjfksdj", "BbbBBBB"}
        };
    }

    @After
    public void removeCourier() {
        courierClient.removeCourier(courier);
    }

    //Периодически сервер лежит и отдает 504 после таймаута в 1 минуту
    @Test
    @DisplayName("Проверка на корректный 404 статус код и некорректные поля при логине в системе")
    @Description("Создаем курьера, пытаемся залогиниться под его учеткой, но с ошибками в логине или пароле")
    public void checkRequiredFieldsLoginCourierRequestError() {
        courierClient.courierCreate(courier, 201);
        courierClient.courierLogin(new Courier(login, password), 404);
    }
}
