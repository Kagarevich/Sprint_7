package yandex.praktikim.nonparameterized;

import com.example.model.Courier;
import com.example.model.ResponseErrorBody;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import yandex.praktikim.BaseCourierTestClass;


public class CourierLoginTest extends BaseCourierTestClass {

    @Test
    @DisplayName("Успех логина курьера в системе - проверка статус кода")
    @Description("Проверяется одновременно успех логина и корректный код ответа, так как проверка через код." +
            "Также проверяется кейс Для авторизации нужно передать все обязательные поля, так как полей" +
            "всего 2")
    public void courierSuccessLoginAndResponseStatusCodeIsCorrect() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier);
        Response response = courierLoginAndSetCourierId(courier);
        removeCourier(courier);
        compareStatusCode(response, 200);
    }

    //Нельзя проверить правильность id, но можно проверить наличие
    //Название поля не имеет смысла спрашивать, так как в случае успеха мы успешно положили поле id из response запроса
    @Test
    @DisplayName("Успех логина курьера в системе - проверка, что в ответе присылается id")
    @Description("ПМы не можем проверить корректность id, но можем проверить его наличие")
    public void courierSuccessLoginResponseBodyIsCorrect() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier);
        Response response = courierLoginAndSetCourierId(courier);
        removeCourier(courier);
        compareResponseBodySuccessLogin(response);
    }

    //Должна быть ошибка и феилд кейса, так как в документации нет одного поля - поля "code". Когда я писал - была 504
    @Test
    @DisplayName("Проверяем тело ответа при 400 ошибке")
    @Description("Отдельная проверка для тела при 400 ошибке")
    public void checkResponseBodyErrorBadRequest() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier);
        courierLoginAndSetCourierId(courier);
        Response response = courierLoginAndSetCourierId(
                new Courier(
                        courier.getLogin(),
                        null
                )
        );
        removeCourier(courier);
        compareResponseBodyError(
                response,
                new ResponseErrorBody("Недостаточно данных для входа")
        );
    }

    //Одиночная проверка тела ответа с ошибкой 404 - она должна упасть, так как документация не совпадает с реализацией
    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть

    @Test
    @DisplayName("Проверка тела ответа при статус коде 404")
    @Description("Проверяю тело ответа для запроса логина курьера при ошибке 404")
    public void checkLoginCourierResponseBodyErrorNotFound() {
        Courier courier = initCustomCourierJavaObject(
                "AaaA",
                "BbbB",
                "CccC"
        );
        sendPostRequestCourier(courier);
        courierLoginAndSetCourierId(courier);
        Courier loginCourier = initCustomCourierJavaObject(
                "AaaA",
                "sdfsdfsdf"
        );
        Response response = courierLoginAndSetCourierId(loginCourier);
        removeCourier(courier);
        compareResponseBodyError(response, new ResponseErrorBody("Учетная запись не найдена"));
    }


    //Отдельно вынес проверку кейса "если авторизоваться под несуществующим пользователем, запрос возвращает ошибку"
    @Test
    @DisplayName("Проверка кода ответа при попытке логина несуществующего пользователя - отдельной проверкой")
    @Description("Проверяю кода ответа для запроса логина курьера при несуществующих кредах")
    public void checkLoginNonExistCourierResponseStatusCode() {
        Courier courier = initCustomCourierJavaObject(
                "AaaA",
                "BbbB",
                "CccC"
        );
        sendPostRequestCourier(courier);
        courierLoginAndSetCourierId(courier);
        Courier loginCourier = initCustomCourierJavaObject(
                "dfgdfgsdfg",
                "sdfsdfsdf"
        );
        Response response = courierLoginAndSetCourierId(loginCourier);
        removeCourier(courier);
        compareStatusCode(response, 404);
    }
}
