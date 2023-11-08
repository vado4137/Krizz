package de.szut.lf8_project.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "employee_assocs")
public class EmployeeAssocEntity {

    @Id
    private EmployeeAssocId id;

    private Long role;
}
