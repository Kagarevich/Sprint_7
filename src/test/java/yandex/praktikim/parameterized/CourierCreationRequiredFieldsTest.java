package yandex.praktikim.parameterized;

import api.client.CourierClient;
import com.example.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//Создал отельный класс для проверок обязательности полей и сделал его параметризованным
@RunWith(Parameterized.class)
public class CourierCreationRequiredFieldsTest {
    private final String login;
    private final String password;
    private final String firstName;

    public CourierCreationRequiredFieldsTest(
            String login,
            String password,
            String firstName
    ) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                //Если нет одного обязательного поля
                {"AaaA", "BbbB", null},
                {"Aaa", null, "Ccc"},
                {null, "Bbb", "Ccc"},
                //Если нет двух обязательных полей
                {"Aaa", null, null},
                {null, null, "Ccc"},
                {null, "Bbb", null},
                //Если нет никаких полей - частный случай
                {null, null, null}
        };
    }

    //Тут реализация не совпадает с документацией, поэтому один тест должно упасть
    @Test
    @DisplayName("Проверка на обязательность (всех) полей в теле запроса + проверка на корректный 400 статус код")
    @Description("Проверка всех частных случаев, когда не хватает каких-то полей" +
            "По документации все поля должны быть, иначе ошибка")
    public void checkRequiredFieldsCreateCourierRequestError() {
        Courier courier = new Courier(
                login,
                password,
                firstName
        );
        CourierClient courierClient = new CourierClient();
        Response response = courierClient.courierCreate(courier, 400);
        if(response.statusCode() == 201) {
            courierClient.removeCourier(courier);
        }
    }
}
