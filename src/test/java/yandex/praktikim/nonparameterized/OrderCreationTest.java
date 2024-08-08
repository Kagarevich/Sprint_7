package yandex.praktikim.nonparameterized;

import com.example.model.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import yandex.praktikim.BaseOrderCreationTestClass;

//Тут отдельные проверки статус кода и тела ответа успешного запроса
//Не тестил тут ошибки, так как об этом в задании не было, а с курьером это указывалось в явной форме
public class OrderCreationTest extends BaseOrderCreationTestClass {


    //Не нашел метода удаления заказа
    //метод отмены заказа всегда возвращает 400 ошибку "message": "Недостаточно данных для поиска",
    // потому что не написано, а какие еще данные нужны и
    //как их применить (в доках этого нет)

    //Завершение заказа не стал использовать, так как данные все равно не удаляются, а просто меняется статус
    // + для завершения заказа нужно сделать следующие шаги:
    // создать заказ -> создать курьера -> залогиниться курьером -> принять заказ (если его не принять,
    // то завершение будет возвращать 409 ошибку) -> получить id заказа и т.д...
    // В общем и целом непонятно, такая цепочка должна быть и её надо использовать или это шутка

    //поэтому тестовые данные не получится удалить, а с такими запросами и не попытаться даже :)
    @Test
    @DisplayName("Проверка на корректный статус код при создании заказа - должен быть 201")
    @Description("Проверка на 201 статус код - не указан в списке проверок, но указан в блоке " +
            "Как будут оценивать твою работу, пункт 11")
    public void createOrderRequestIsSuccessAndResponseStatusCodeIsCreated() {
        Order order = initCustomColorOrderJavaObject(new String[] {"BLACK", "GREY"});
        Response response = sendPostRequestOrder(order);
        compareStatusCode(response, 201);
    }

    @Test
    @DisplayName("Корректное тело ответа запроса создания заказа")
    @Description("Проверяем через мэтчинг данных")
    public void successOrderCreateRequestCorrectResponseBody() {
        Order order = initCustomColorOrderJavaObject(new String[] {"BLACK"});
        Response response = sendPostRequestOrder(order);
        getOrderTrackAfterOrderCreateDataMatching(response);
    }
}
