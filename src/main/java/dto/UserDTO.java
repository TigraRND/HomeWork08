package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
public class UserDTO {

    private Long id;
    private Long userStatus;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}