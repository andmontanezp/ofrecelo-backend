package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.exceptions.ExistingUserException;
import cl.ofrecelo.api.offer.exceptions.NoExistingUserException;
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
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if(existingUser.isPresent()) {
            throw new ExistingUserException(existingUser.get().getEmail());
        }else {
            user = userRepository.save(user);
        }

        return userRepository.save(user);
    }

    @Override
    public Boolean resetPassword(String email) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(existingUser.isEmpty()){
            throw new NoExistingUserException(email);
        }else{
            String newPassword = generatePassword(8).toString();
            User user= userRepository.updatePassword(email, passwordEncoder.encode(newPassword));
            sendSimpleMessage(email, "Recuperar contraseña",
                    "Hola, "+user.getName()+ "tu nueva contraseña: "+ newPassword);
            return true;
        }
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
