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
public class CourierCreationRequiredFieldsTest extends BaseCourierTestClass {
    private final String login;
    private final String password;
    private final String firstName;
    private final Integer expectedStatusCode; //тело проверяю отдельное в классе без параметризации

    public CourierCreationRequiredFieldsTest(
            String login,
            String password,
            String firstName,
            Integer expectedStatusCode
    ) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedStatusCode = expectedStatusCode;
    }

    //написал все варианты, в том числе и частные случаи

    //Тут и для кейса "чтобы создать курьера, нужно передать в ручку все обязательные поля"
    //И для кейса "если одного из полей нет, запрос возвращает ошибку",
    //Так как по документации все поля должны быть и они все обязательные
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                //Если нет одного обязательного поля
                {"AaaA", "BbbB", null, 400},
                {"Aaa", null, "Ccc", 400},
                {null, "Bbb", "Ccc", 400},
                //Если нет двух обязательных полей
                {"Aaa", null, null, 400},
                {null, null, "Ccc", 400},
                {null, "Bbb", null, 400},
                //Если нет никаких полей - частный случай
                {null, null, null, 400}
        };
    }

    //Тут реализация не совпадает с документацией, поэтому один тест должно упасть
    @Test
    @DisplayName("Проверка на обязательность (всех) полей в теле запроса + проверка на корректный 400 статус код")
    @Description("Проверка всех частных случаев, когда не хватает каких-то полей" +
            "По документации ВСЕ поля должны быть, иначе ошибка")
    public void checkRequiredFieldsCreateCourierRequestError() {
        Courier courier = initCustomCourierJavaObject(
                login,
                password,
                firstName
        );
        Response response = sendPostRequestCourier(courier);
        if(response.statusCode() == 201) {
            removeCourier(courier);
        }
        compareStatusCode(response, expectedStatusCode);
    }
}
