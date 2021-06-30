package User;

import dto.UserDTO;
import dto.UserResponseDTO;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserAPI;

import static org.hamcrest.Matchers.*;

public class UserTest {
    private UserAPI userAPI = new UserAPI();

//    @Test
    @DisplayName("Создание пользователя Matchers")
    public void checkUserCreation(){
//      Проверка создания пользователя с использованием матчеров
        UserDTO user = UserDTO.builder()
                .id(12001)
                .userStatus(1)
                .username("ALovi")
                .password("Capitolka86")
                .firstName("Andrew")
                .lastName("Lovarsky")
                .phone("8-904-12-12-346")
                .email("ALovi86@mail.ru")
                .build();

        Response response = userAPI.createUser(user);
        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("type", equalTo("unknown"))
                .body("message", equalTo("12001"))
                .time(lessThan(5000L));

    }

//    @Test
    @DisplayName("Создание пользователя JSONPath")
    public void checkUserCreationJSONPath(){
//      Проверка создания пользователя с использованием JSONPath
        UserDTO user = UserDTO.builder()
                .id(12001)
                .userStatus(1)
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
        userAPI.createUser(user)
                .jsonPath().get("type"));
    }

//    @Test
    @DisplayName("Создание пользователя DTO конвертер")
    public void checkUserCreationResponseDTO(){
//      Проверка создания пользователя с использованием конвертации ответа в DTO
        UserDTO user = UserDTO.builder()
                .id(12001)
                .userStatus(1)
                .username("ALovi")
                .password("Capitolka86")
                .firstName("Andrew")
                .lastName("Lovarsky")
                .phone("8-904-12-12-346")
                .email("ALovi86@mail.ru")
                .build();

        Response response = userAPI.createUser(user);
        String messageActual = response.as(UserResponseDTO.class).getMessage();
        Assertions.assertEquals("12001", messageActual);
    }

    @Test
    @DisplayName("Пользователь не найден")
    public void checkUserNotFound(){
//      Проверка поиска пользователя с негативным результатом
        Response response = userAPI.searchUserByName("Spitzgold");
        response
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        logging(response);
    }

    @Test
    @DisplayName("Пользователь найден")
    public void checkUserFound(){
//      Проверка поиска пользователя с позитивным результатом
        Response response = userAPI.searchUserByName("string");
        response
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("userStatus", equalTo(0));
        logging(response);
    }

    private void logging(Response response){
//      Вспомогательный метод для красивого логирования тела запроса
        System.out.println("\nResponse body is:");
        response.body().prettyPrint();
    }
}