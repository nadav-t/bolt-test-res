package mend.users.mappers;

import mend.users.dtos.UserUpdateDTO;
import mend.users.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserUpdateMapper {
    UserUpdateDTO toDTO(User user);
    User toEntity(UserUpdateDTO userCreateDTO);
}

