package de.szut.lf8_project.dtos;

import lombok.Data;

@Data
public class GetEmployeeDTO extends DTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String street;
    private String postcode;
    private String city;
    private String phone;
    private GetQualificationForEmployeeDTO[] skillSet;
}
