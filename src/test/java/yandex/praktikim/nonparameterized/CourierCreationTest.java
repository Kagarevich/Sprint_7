package yandex.praktikim.nonparameterized;

import com.example.model.Courier;
import com.example.model.ResponseErrorBody;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import yandex.praktikim.BaseCourierTestClass;

//Очищение тестовых данных реализовал через логин (чтобы найти его id) и удаление курьера
public class CourierCreationTest extends BaseCourierTestClass {

    @Test
    @DisplayName("Проверка доступности ручки и одновременная проверка " +
            "на 201 код статус ответа запроса создания курьера")
    @Description("Я не нашел запроса на получение списка или одного курьера, поэтому просто проверяю его как сам факт, " +
            "что пришел ответ 201 на создание")
    public void createCourierRequestIsSuccessAndResponseStatusCodeIsCreated() {
        Courier courier = initCourierJavaObject(); // вывел в шаг для наглядности
        Response response = sendPostRequestCourier(courier);
        //Чтобы получить id курьера, приходится логиниться, иначе его не узнать
        removeCourier(courier);
        compareStatusCode(response, 201);
    }

    @Test
    @DisplayName("Проверяю тело ответа успешного запроса создания курьера")
    @Description("вывел эту проверку в отдельный тест, чтобы было нагляднее")
    public void successCreateCourierRequestBodyIsCorrect() {
        Courier courier = initCourierJavaObject();
        Response response = sendPostRequestCourier(courier);
        removeCourier(courier);
        compareResponseBodyStatusCodeCreated(response);
    }

    @Test
    @DisplayName("Тест, что нельзя создать двух одинаковых курьеров")
    @Description("проверка, что нельзя создать двух одинаковых курьеров + правильный статус код")
    public void cannotCreateDuplicateCourier() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier); //создали первого
        Response response = sendPostRequestCourier(courier); //попытка создать дубликат
        removeCourier(courier);
        compareStatusCode(response, 409);
    }

    @Test
    @DisplayName("Тест, что нельзя создать двух курьеров с одинаковыми логинами")
    @Description("эта проверка была выписана отдельной строкой в задании + правильный статус код")
    public void cannotCreateDuplicateCourierLogin() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier); //создали первого
        Courier courierSameLogin = initCourierJavaObject();
        courierSameLogin.setPassword("Password");
        courierSameLogin.setFirstName("Gosling");
        Response response = sendPostRequestCourier(courierSameLogin); //попытка создать с тем же логином
        //если проверка упадет
        removeCourier(courier);
        //если создаться дубликат
        removeCourier(courierSameLogin);
        compareStatusCode(response, 409);

    }

    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть
    //удалял каждый раз аккаунт руками через постман, так как кейс корректный
    //пришлось передвинуть удаление тестовых данных перед проверкой, так как иначе это блочило бы тестирование
    @Test
    @DisplayName("Проверяю тело ответа запроса создания курьера с ошибкой 409")
    @Description("вывел эту проверку в отдельный тест, чтобы было нагляднее")
    public void notSuccessCreateCourierResponseBodySameLogin() {
        Courier courier = initCourierJavaObject();
        sendPostRequestCourier(courier);
        Courier courierSameLogin = initCourierJavaObject();
        courierSameLogin.setPassword("Password");
        courierSameLogin.setFirstName("Gosling");
        Response response = sendPostRequestCourier(courierSameLogin);
        //если проверка упадет
        removeCourier(courier);
        //если создаться дубликат
        removeCourier(courierSameLogin);
        compareResponseBodyError(
                response,
                new ResponseErrorBody("Этот логин уже используется. Попробуйте другой."));
    }

    //Одиночная проверка тела ответа с ошибкой 400 - она должна упасть, так как документация не совпадает с реализацией
    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть

    @Test
    @DisplayName("Проверка тела ответа при статус коде 400")
    @Description("Проверяю тело ответа для запроса создания курьера при ошибке 400")
    public void checkCreateCourierResponseBodyErrorBadRequest() {
        Courier courier = initCustomCourierJavaObject(
                null,
                null,
                null
        );
        Response response = sendPostRequestCourier(courier);
        if(response.statusCode() == 201) {
            removeCourier(courier);
        }
        compareResponseBodyError(
                response,
                new ResponseErrorBody("Недостаточно данных для создания учетной записи")
        );
    }
}
