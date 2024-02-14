package mend.users.configurations;

import mend.users.mappers.UserCreateMapper;
import mend.users.mappers.UserMapper;
import mend.users.mappers.UserUpdateMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configurations {

    @Bean
    public WebClient.Builder getWebClient() {
        return WebClient.builder();
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public UserCreateMapper userCreateMapper() {
        return Mappers.getMapper(UserCreateMapper.class);
    }

    @Bean
    public UserUpdateMapper userUpdateMapper() {
        return Mappers.getMapper(UserUpdateMapper.class);
    }
}
