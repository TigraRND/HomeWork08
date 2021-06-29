package User;

import dto.OrderDTO;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import services.StoreAPI;

import static org.hamcrest.Matchers.lessThan;

public class OrderTest {
    StoreAPI storeAPI = new StoreAPI();

    @Test
    public void checkPlaceOrder(){
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
}
