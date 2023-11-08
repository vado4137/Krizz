package de.szut.lf8_project.service.project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetProjectDTO {

    private Long employeeId;

    private Long clientId;

    private String contactName;

    private String description;

    private Date start;

    private Date scheduledEnd;

    private Date actualEnd;
}
