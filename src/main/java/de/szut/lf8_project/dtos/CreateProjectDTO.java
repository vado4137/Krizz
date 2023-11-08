package de.szut.lf8_project.dtos;

import de.szut.lf8_project.exceptionHandling.CanBeNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateProjectDTO extends DTO {

    private String description;
    private Long clientId;
    private String contactName;
}
