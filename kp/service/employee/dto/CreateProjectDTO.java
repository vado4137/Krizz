package de.szut.lf8_project.service.employee.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateProjectDTO {

    private String description;
    private String contactName;
    private List<CreateEmployeeDTO> employees;
    private Long clientId;
}
