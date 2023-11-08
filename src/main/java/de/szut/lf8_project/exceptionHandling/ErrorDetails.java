package de.szut.lf8_project.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private String details;
    private Date timestamp;
}
