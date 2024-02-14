package mend.users.resources;

import jakarta.validation.Valid;
import mend.users.dtos.UserCreateDTO;
import mend.users.dtos.UserDTO;
import mend.users.dtos.UserUpdateDTO;
import mend.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserDTO> getUsers() {
        return Flux.fromIterable(userService.getUsers());
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> getUser(@PathVariable String id) {
        try {
            return Mono.just(userService.getUser(id));
        }
        catch (Exception e) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to get user"));
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody @Valid UserCreateDTO user) {
        try {
            return Mono.just(userService.createUser(user));
        }
        catch (Exception e) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create user"));
        }
    }

    @PatchMapping("/{id}")
    public Mono<UserDTO> updateUser(@PathVariable String id,
                                                 @RequestBody @Valid UserUpdateDTO updateData) {
        try {
            return Mono.just(userService.updateUser(id, updateData));
        }
        catch (Exception e) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update user"));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return Mono.justOrEmpty(null);
    }
}
