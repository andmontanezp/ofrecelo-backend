package cl.ofrecelo.api.offer.exceptions;

import java.net.http.HttpClient;

public class UserAlreadyCreatedException extends RuntimeException {
    public UserAlreadyCreatedException(String email){
        super(String.format("User with email %s already exits", email));
    }
}
