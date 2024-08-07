package yandex.praktikim;

import com.example.model.Courier;
import com.example.model.ResponseCourierIdBody;
import com.example.model.ResponseOkBody;
import com.example.model.ResponseErrorBody;
import com.example.repository.CourierRepository;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;

public class BaseCourierTestClass extends BaseTestClass {

    @Step("Создаю кастомный объект пользователя для создания")
    public Courier initCustomCourierJavaObject(String login, String password, String firstName) {
        return new Courier(
                login,
                password,
                firstName
        );
    }

    @Step("Создаю кастомный объект пользователя для логина")
    public Courier initCustomCourierJavaObject(String login, String password) {
        return new Courier(
                login,
                password
        );
    }


    @Step("создаю дефолтный объект курьера")
    public Courier initCourierJavaObject() {
        return new Courier(
                "RyanGoslingg",
                "Drive",
                "Ryan"
        );
    }

    @Step("Отправляю POST запрос создания курьера /api/v1/courier")
    public Response sendPostRequestCourier(Courier courier) {
        return CourierRepository.courierCreate(courier);
    }

    @Step("Находим id курьера с бэка и сетаем его в наш java объект +" +
            "вывел нахождение в отдельный подшаг, чтобы сразу было понятно, если" +
            "логин отвалится"
    )
    public Response courierLoginAndSetCourierId(Courier courier) {

        Response response = CourierRepository.courierLogin(courier);
        courier.setId(response.body().path("id"));
        return response;
    }

    @Step("Удаление курьера по его id, которое узнаем через запрос /api/v1/courier/login")
    public void removeCourier(Courier courier) {
        courierLoginAndSetCourierId(courier);
        CourierRepository.courierRemove(String.valueOf(courier.getId()));
    }

    @Step("Проверка успешного тела ответа запроса создания курьера")
    public void compareResponseBodyStatusCodeCreated(Response response, ResponseOkBody expected) {
        ResponseOkBody okBody = response.body().as(ResponseOkBody.class);
        Assert.assertEquals(
                "Ожидалось другое тело ответа",
                expected.getOk(),
                okBody.getOk()
        );
    }

    @Step("Проверка наличия возвращения id при успешном логине курьера")
    public void compareResponseBodySuccessLogin(Response response) {
        ResponseCourierIdBody courierIdBody = response.body().as(ResponseCourierIdBody.class);
        Assert.assertEquals(
                "Ожидалось поле id",
                Integer.class,
                courierIdBody.getId().getClass()
        );
    }

    @Step("Проверка на одинаковые тела ответов для ошибок, состоящих из одного поля message")
    public void compareResponseBodyError(Response response, ResponseErrorBody expected) {
        ResponseErrorBody responseErrorBody = response.body().as(ResponseErrorBody.class);
        Assert.assertEquals(
                "Ожидалось другое тело ответа",
                expected.getMessage(),
                responseErrorBody.getMessage()
        );
    }
}
