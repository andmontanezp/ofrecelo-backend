package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findOne(@PathVariable("email") String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

}
