package cl.ofrecelo.api.offer.exceptions;

public class NoExistingUserException extends RuntimeException{
    public NoExistingUserException(String email){

        super(String.format("User with email %s NO exits", email));
    }
}
