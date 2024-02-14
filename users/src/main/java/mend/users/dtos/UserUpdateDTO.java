package mend.users.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    @Email private String email;
}
