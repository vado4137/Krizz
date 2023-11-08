package de.szut.lf8_project.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongIdFormatException extends RuntimeException {
    public WrongIdFormatException(String message) {
        super(message);
    }
}
