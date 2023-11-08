package de.szut.lf8_project.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GetProjectDTO extends DTO {

    private String description;

    private List<GetEmployeeAssocDTO> employees;

    private String contactName;

    private Long clientId;
}
