package services;

import dto.OrderDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StoreAPI {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private RequestSpecification spec;
    private static final String ORDER = "/store/order";
    private static final String INVENTORY = "/store/inventory";

    public StoreAPI(){
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    public Response createOrder(OrderDTO order){
        return
                given(spec)
                    .with()
                        .body(order)
                        .log().all()
                    .when()
                        .post(ORDER);
    }
}
