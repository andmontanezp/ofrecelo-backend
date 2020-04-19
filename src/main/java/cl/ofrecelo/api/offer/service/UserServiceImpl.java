package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.exceptions.ExistingUserException;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if(existingUser.isPresent())
            throw new ExistingUserException(existingUser.get().getEmail());

        return userRepository.save(user);
    }
}
