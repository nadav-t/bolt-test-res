package mend.users.services;

import mend.users.dtos.UserCreateDTO;
import mend.users.dtos.UserDTO;
import mend.users.dtos.UserUpdateDTO;
import mend.users.mappers.UserCreateMapper;
import mend.users.mappers.UserMapper;
import mend.users.mappers.UserUpdateMapper;
import mend.users.models.User;
import mend.users.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserCreateMapper userCreateMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       UserCreateMapper userCreateMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userCreateMapper = userCreateMapper;
    }

    public UserDTO getUser(String id) {
        return userRepository
                .findById(id)
                .map(userMapper:: toDTO)
                .orElse(null);
    }

    public List<UserDTO> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper:: toDTO)
                .collect(toList());
    }

    public UserDTO createUser(UserCreateDTO user) {
        User userToCreate = userCreateMapper.toEntity(user);
        User createdUser = userRepository.save(userToCreate);
        return userMapper.toDTO(createdUser);
    }

    public UserDTO updateUser(String id, UserUpdateDTO updateDTO) {
        return userRepository
                .findById(id)
                .map(existingUser ->
                {
                    BeanUtils.copyProperties(updateDTO, existingUser, getNullPropertyNames(updateDTO));
                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toDTO(updatedUser);
                })
                .orElse(null);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Utils
    // Helper method to get property names with null values
    private String[] getNullPropertyNames(UserUpdateDTO source) {
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = sourceWrapper.getPropertyDescriptors();

        List<String> nullProperties = new ArrayList<>();
        for (PropertyDescriptor descriptor : descriptors) {
            String propertyName = descriptor.getName();
            Object propertyValue = sourceWrapper.getPropertyValue(propertyName);
            if (propertyValue == null) {
                nullProperties.add(propertyName);
            }
        }

        return nullProperties.toArray(new String[0]);
    }
}