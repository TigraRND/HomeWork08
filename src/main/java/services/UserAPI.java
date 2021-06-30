package services;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserAPI {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private RequestSpecification spec;
    private static final String USER = "/user";

    public UserAPI(){
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    public Response createUser(UserDTO user){
        return
                given(spec)
                .with()
                        .body(user)
                        .log().all()
                .when()
                .post(USER);
    }

    public Response searchUserByName(String name){
        return
                given(spec)
                    .with()
                    .log().all()
                .when()
                .get(USER + "/" + name);
    }
}