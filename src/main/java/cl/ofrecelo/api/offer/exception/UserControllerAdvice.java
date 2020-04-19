package cl.ofrecelo.api.offer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Object> userNotFoundHandler(UserNotFoundException e) {
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
