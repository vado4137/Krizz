package de.szut.lf8_project.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateEmployeeAssocDTO {

    @NotNull(message = "employeeId must not be empty")
    private Long employeeId;

    @NotNull(message = "role must not be empty")
    private Long role;
}
