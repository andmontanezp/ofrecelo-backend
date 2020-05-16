package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.UserRepository;
import cl.ofrecelo.api.offer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findOne(@PathVariable("email") String email) throws UserNotFoundException {
        return ResponseEntity.ok(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException()));
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.resetPassword(email));
    }

    @GetMapping("/validateEmail/{cod}")
    public ResponseEntity<String> validateTemporalPassword(@PathVariable("password") String cod) {
        return ResponseEntity.ok(userService.validateEmail(cod));
    }

}
