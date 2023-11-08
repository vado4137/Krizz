package de.szut.lf8_project.entities;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

@Data
public class EmployeeAssocId implements Serializable {

    @Id
    private Long employeeId;

    @Id
    private Long projectId;
}
