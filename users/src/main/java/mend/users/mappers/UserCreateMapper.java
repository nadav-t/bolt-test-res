package mend.users.mappers;

import mend.users.dtos.UserCreateDTO;
import mend.users.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserCreateMapper {
    UserCreateDTO toDTO(User user);
    User toEntity(UserCreateDTO userCreateDTO);
}

