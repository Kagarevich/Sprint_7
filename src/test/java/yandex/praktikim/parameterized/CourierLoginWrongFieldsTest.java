package yandex.praktikim.parameterized;

import com.example.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.praktikim.BaseCourierTestClass;

@RunWith(Parameterized.class)
public class CourierLoginWrongFieldsTest extends BaseCourierTestClass {
    private final String login;
    private final String password;
    private final Integer expectedStatusCode;

    public CourierLoginWrongFieldsTest(
            String login,
            String password,
            Integer expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
    }

    //Проверка, если неправильный логин или пароль. Решил сделать отдельной проверкой, хотя всё то же самое, что и для
    //CourierLoginRequiredFieldsTest. По заданию это разные проверки

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"AaaA", "fasdfdfagdfgsdf", 404},
                {"skdfjskdjfksdj", "BbbB", 404}
        };
    }

    //Периодически сервер лежит и отдает 504 после таймаута в 1 минуту
    @Test
    @DisplayName("Проверка на корректный 404 статус код и некорректные поля при логине в системе")
    @Description("")
    public void checkRequiredFieldsLoginCourierRequestError() {
        Courier courier = initCustomCourierJavaObject(
                "AaaA",
                "BbbB",
                "CccC"
        );
        sendPostRequestCourier(courier);
        courierLoginAndSetCourierId(courier);
        Courier loginCourier = initCustomCourierJavaObject(
                login,
                password
        );
        Response response = courierLoginAndSetCourierId(loginCourier);
        removeCourier(courier);
        compareStatusCode(response, expectedStatusCode);
    }
}
