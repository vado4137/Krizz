package de.szut.lf8_project.exceptionHandling;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.regex.Pattern;

@ControllerAdvice
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "invalid JSON posted",
                content = @Content)
})
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongIdFormatException.class)
    public ResponseEntity<ErrorDetails> handleWrongIdFormatException(WrongIdFormatException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllOtherExceptions(Exception exception, WebRequest request) {
        String details = request.getDescription(false);
        if (exception instanceof HttpMessageNotReadableException) {
            if (Pattern.matches(".*/api/projects/\\d+/employees.*", details)) {
                return handleWrongIdFormatException(new WrongIdFormatException("Body is missing. Please type in the employeeId"), request);
            }
        }
        System.out.println(exception.getClass());
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), details, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
