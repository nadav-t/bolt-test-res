package mend.users.resources;

import jakarta.validation.Valid;
import mend.users.dtos.UserCreateDTO;
import mend.users.dtos.UserDTO;
import mend.users.dtos.UserUpdateDTO;
import mend.users.services.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    // TODO add status code handling

    @GetMapping
    public Flux<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody @Valid UserCreateDTO user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public Mono<UserDTO> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDTO user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
