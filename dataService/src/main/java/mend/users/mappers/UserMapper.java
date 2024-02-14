package mend.users.mappers;

import mend.users.dtos.UserDTO;
import mend.users.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}

