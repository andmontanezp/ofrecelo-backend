package cl.ofrecelo.api.offer.exceptions;

public class UserDoesNotExistsException extends RuntimeException{
    public UserDoesNotExistsException(String email){

        super(String.format("User with email %s NO exits", email));
    }
}
