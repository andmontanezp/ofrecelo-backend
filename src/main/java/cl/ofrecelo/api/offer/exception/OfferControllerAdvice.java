package cl.ofrecelo.api.offer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OfferControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Object> offerNotFoundHandler(OfferNotFoundException e) {
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
