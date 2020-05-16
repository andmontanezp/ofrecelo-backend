package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.exceptions.UserAlreadyCreatedException;
import cl.ofrecelo.api.offer.exceptions.UserDoesNotExistsException;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;


import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;


    public UserServiceImpl(  UserRepository userRepository,  PasswordEncoder passwordEncoder,
                           JavaMailSender emailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    @Override
    public User create(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.findByEmail(user.getEmail()).ifPresentOrElse(
                existingUser -> {
                    throw new UserAlreadyCreatedException(existingUser.getEmail());
                },
                () -> userRepository.save(user)
        );

        return user;
    }

    @Override
    public Boolean resetPassword(String email) {
        User existingUser = userRepository.findByEmail(email).orElseThrow(() -> new UserDoesNotExistsException(email));

        String newPassword = generatePassword(8).toString();

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser = userRepository.save(existingUser);
        sendSimpleMessage(email, "Recuperar contraseña",
                "Hola, "+existingUser.getName()+ "tu nueva contraseña: "+ newPassword);
        return true;
    }

    @Override
    public String validateEmail(String cod) {
        return null;
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    private static char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
