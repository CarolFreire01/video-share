package br.com.carol.videoshare.expections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createException(badRequestException, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException badRequestException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createException(badRequestException, HttpStatus.NOT_FOUND));
    }

    private StandardException createException(Exception exception, HttpStatus status) {
        return StandardException.builder()
                .status(status.value())
                .message(exception.getMessage())
                .build();
    }
}
