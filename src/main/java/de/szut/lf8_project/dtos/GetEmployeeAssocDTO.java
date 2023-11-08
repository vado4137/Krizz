package de.szut.lf8_project.dtos;

import lombok.Data;

@Data
public class GetEmployeeAssocDTO extends DTO {

    private Long employeeId;
    private Long role;
}
