package mend.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User {
    @NonNull
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}