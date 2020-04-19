package cl.ofrecelo.api.offer.exceptions;

public class ExistingUserException extends RuntimeException{
    public ExistingUserException(String email){
        super(String.format("User with email %s already exits", email));
    }
}
