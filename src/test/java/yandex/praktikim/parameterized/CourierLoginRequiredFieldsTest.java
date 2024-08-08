package yandex.praktikim.parameterized;

import com.example.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.praktikim.BaseCourierTestClass;

//Создал отельный класс для проверок обязательности полей и сделал его параметризованным
@RunWith(Parameterized.class)
public class CourierLoginRequiredFieldsTest extends BaseCourierTestClass {
    private final String login;
    private final String password;
    private final Integer expectedStatusCode;

    public CourierLoginRequiredFieldsTest(
            String login,
            String password,
            Integer expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
    }

    //написал все варианты, в том числе и частные случаи

    //Тут и для кейса "для авторизации нужно передать все обязательные поля" (так как полей всего два, то в остальных
    // случаях ответ 400 статус код)
    //И для кейса "если какого-то поля нет, запрос возвращает ошибку",
    //Так как по документации все поля должны быть и они все обязательные
    //200 статус проверен отдельно
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"AaaA", null, 400},
                {null, "BbbB", 400},
                {null, null, 400},
        };
    }


    //Периодически сервер таймаутит, не знаю с чем это связано
    @Test
    @DisplayName("Проверка на обязательность (всех) полей в теле запроса + проверка на корректный 400 статус код")
    @Description("Проверка всех частных случаев, когда не хватает каких-то полей" +
            "По документации ВСЕ поля должны быть, иначе ошибка")
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
