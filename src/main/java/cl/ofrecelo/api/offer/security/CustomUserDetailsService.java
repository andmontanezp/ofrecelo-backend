package cl.ofrecelo.api.offer.security;


import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;


@Component
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("The username %s doesn't exist", userName))
                );

        return new CustomUserDetails(user, new HashSet<>(new ArrayList<>()));

    }
}
