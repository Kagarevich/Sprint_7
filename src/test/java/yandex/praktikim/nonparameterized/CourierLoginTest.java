package yandex.praktikim.nonparameterized;

import api.client.CourierClient;
import com.example.model.Courier;
import com.example.model.ResponseErrorBody;
import com.example.model.generator.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void init() {
        courierClient = new CourierClient();
        courier = CourierGenerator.create();
    }

    @Test
    @DisplayName("Успех логина курьера в системе - проверка статус кода")
    @Description("Проверяется одновременно успех логина и корректный код ответа, так как проверка через код." +
            "Также проверяется кейс Для авторизации нужно передать все обязательные поля, так как полей" +
            "всего 2 + проверка тела ответа при логине (то есть смотрим наличие id)")
    public void courierSuccessLoginResponseStatusCodeAndBodyIsCorrect() {
        courierClient.courierCreate(courier, 201);
        Response response = courierClient.courierLogin(courier, 200);
        courierClient.compareResponseBodySuccessLogin(response);
        courierClient.removeCourier(courier);
    }

    //Должна быть ошибка и феил кейса, так как в документации нет одного поля - поля "code"
    @Test
    @DisplayName("Проверяем тело ответа при 400 ошибке")
    @Description("Отдельная проверка для тела при 400 ошибке")
    public void checkResponseBodyErrorBadRequest() {
        courierClient.courierCreate(courier, 201);
        Response response = courierClient.courierLogin(
                new Courier(null, courier.getPassword()),
                400);
        courierClient.compareResponseBodyError(
                response,
                new ResponseErrorBody("Недостаточно данных для входа")
        );
        courierClient.removeCourier(courier);
    }

    //Одиночная проверка тела ответа с ошибкой 404 - она должна упасть, так как документация не совпадает с реализацией
    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть

    @Test
    @DisplayName("Проверка тела ответа при статус коде 404 Учетная запись не найдена")
    @Description("Проверяю тело ответа для запроса логина курьера при ошибке 404 Учетная запись не найдена")
    public void checkLoginCourierResponseBodyErrorNotFound() {
        Response response = courierClient.courierLogin(courier, 404);
        courierClient.compareResponseBodyError(response, new ResponseErrorBody("Учетная запись не найдена"));
    }
}
