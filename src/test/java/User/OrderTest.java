package User;

import dto.OrderDTO;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.StoreAPI;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTest {
    StoreAPI storeAPI = new StoreAPI();

    @Test
    @DisplayName("Проверка валидного размещения заказа")
    public void checkValidPlaceOrder(){
// Проверка HTTP ответа 200 и времени отклика сервиса при
// валидном размещении заказа

        OrderDTO order = OrderDTO.builder()
                .id(1288)
                .petId(123532)
                .quantity(1)
                .shipDate("2021-06-29T00:29:45.331Z")
                .status("waiting for approve")
                .complete(false)
                .build();

        Response response = storeAPI.createOrder(order);
        response
                .then()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
                .log().all();
    }

    @Test
    @DisplayName("Проверка невалидного размещения заказа")
    public void checkInvalidPlaceOrder(){
// Проверка всех вариантов возврата 400 ошибки
// тест падает так как находит реальный дефект

        //Отсутствует id заказа
        OrderDTO[] order = new OrderDTO[3];
        order[0] = OrderDTO.builder()
                .petId(123532)
                .quantity(1)
                .shipDate("2021-06-29T00:29:45.331Z")
                .status("placed")
                .complete(false)
                .build();

        //Отсутствует id товара
        order[1] = OrderDTO.builder()
                .id(1288)
                .quantity(1)
                .shipDate("2021-06-29T00:29:45.331Z")
                .status("placed")
                .complete(false)
                .build();

        //Не указано количество товара
        order[2] = OrderDTO.builder()
                .id(1288)
                .petId(123532)
                .shipDate("2021-06-29T00:29:45.331Z")
                .status("placed")
                .complete(false)
                .build();

        for(int i = 0; i < 3; i++){
            storeAPI.createOrder(order[i])
                    .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .log().all();
        }
    }

    @Test
    @DisplayName("Проверка инвентаря")
    public void checkInventory(){
// Так как метод каждый раз возвращает разный сгенерированный body
// то проверяем просто что body не пустой

        storeAPI.getInventory()
                .then()
                .body(notNullValue())
                .log().body();
    }
}