package mend.users.services;

import mend.users.aspects.UsersLoggingAspect;
import mend.users.dtos.UserCreateDTO;
import mend.users.dtos.UserDTO;
import mend.users.dtos.UserUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;


@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${retry.max}")
    private int maxRetry;

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder,
                       @Value("${dataService.url}") String dataServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(dataServiceUrl).build();
    }

    public Flux<UserDTO> getAllUsers() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(1)))
                .onErrorResume(error -> Flux.error(new RuntimeException("Failed to fetch users")));
    }

    public Mono<UserDTO> getUserById(String userId) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnSuccess(result -> logger.info("Successfully get user: {}", result))
                .doOnError(error -> logger.error("Failed to get user. Error: {}", error.getMessage()))
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(1)))
                .onErrorResume(error -> Mono.error(new RuntimeException("Failed to fetch user " + userId)));
    }

    public Mono<UserDTO> createUser(UserCreateDTO user) {
        return webClient.post()
                .uri("/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnSuccess(result -> logger.info("Successfully created user: {}", result))
                .doOnError(error -> logger.error("Failed to create user. Error: {}", error.getMessage()))
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(1)))
                .onErrorResume(error -> Mono.error(new RuntimeException("Failed to create user")));
    }


    public Mono<UserDTO> updateUser(String userId, UserUpdateDTO user) {
        return webClient.put()
                .uri("/users/{id}", userId)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnSuccess(result -> logger.info("Successfully updated user: {}", result))
                .doOnError(error -> logger.error("Failed to update user. Error: {}", error.getMessage()))
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(1)))
                .onErrorResume(error -> Mono.error(new RuntimeException("Failed to update user " + userId)));
    }

    public Mono<String> deleteUser(String userId) {
        return webClient.delete()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(result -> logger.info("Successfully deleted user: {}", userId))
                .doOnError(error -> logger.error("Failed to delete user. Error: {}", error.getMessage()))
                .thenReturn("User deleted successfully")
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(1)))
                .onErrorResume(error -> Mono.error(new RuntimeException("Error deleting user: " + error.getMessage())));
    }
}