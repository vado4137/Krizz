package de.szut.lf8_project.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateProjectDTO {

    @NotBlank(message = "description must not be blank")
    @Size(min = 3, message = "description must at least have a length of 3")
    private String description;

    @NotNull(message = "clientId must not be empty")
    private Long clientId;

    @NotBlank(message = "contactName must not be blank")
    @Size(min = 3, message = "description must at least have a length of 3")
    private String contactName;
}
