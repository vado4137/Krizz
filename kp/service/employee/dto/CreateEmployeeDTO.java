package de.szut.lf8_project.service.employee.dto;

import javax.persistence.*;
import lombok.Data;

@Data
public class CreateEmployeeDTO {

    @Id
    private Long id;

    private Long role;
}
