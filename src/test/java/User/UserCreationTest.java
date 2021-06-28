package User;

import dto.UserDTO;
import dto.UserResponseDTO;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserBuilder;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class UserCreationTest {
    private UserBuilder userBuilder = new UserBuilder();

    @Test
    public void checkUserCreation(){
        UserDTO user = UserDTO.builder()
                .id(12001L)
                .userStatus(01L)
                .username("ALovi")
                .password("Capitolka86")
                .firstName("Andrew")
                .lastName("Lovarsky")
                .phone("8-904-12-12-346")
                .email("ALovi86@mail.ru")
                .build();

        Response response = userBuilder.createUser(user);
        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("type", equalTo("unknown"))
                .body("message", equalTo("12001"))
                .time(lessThan(5000L));

    }

    @Test
    public void checkUserCreationJSONPath(){
        UserDTO user = UserDTO.builder()
                .id(12001L)
                .userStatus(01L)
                .username("ALovi")
                .password("Capitolka86")
                .firstName("Andrew")
                .lastName("Lovarsky")
                .phone("8-904-12-12-346")
                .email("ALovi86@mail.ru")
                .build();

//        Response response = userBuilder.createUser(user);
//        String messageActual = response.jsonPath().get("message");
//        Assertions.assertEquals("12001", messageActual);

        Assertions.assertEquals("unknown",
        userBuilder.createUser(user)
                .jsonPath().get("type"));
    }

    @Test
    public void checkUserCreationResponseDTO(){
        UserDTO user = UserDTO.builder()
                .id(12001L)
                .userStatus(01L)
                .username("ALovi")
                .password("Capitolka86")
                .firstName("Andrew")
                .lastName("Lovarsky")
                .phone("8-904-12-12-346")
                .email("ALovi86@mail.ru")
                .build();

        Response response = userBuilder.createUser(user);
        String messageActual = response.as(UserResponseDTO.class).getMessage();
        Assertions.assertEquals("12001", messageActual);
    }
}