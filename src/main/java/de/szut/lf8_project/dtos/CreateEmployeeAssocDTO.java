package de.szut.lf8_project.dtos;

import lombok.Data;

@Data
public class CreateEmployeeAssocDTO extends DTO {

    private Long employeeId;
    private Long role;
}
