package de.szut.lf8_project.service.project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import de.szut.lf8_project.service.employee.EmployeeEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<EmployeeEntity> employees;

    private Long clientId;

    @NotNull
    private String contactName;

    @NotNull
    private String description;

//    private Date start;
//
//    private Date scheduledEnd;
//
//    private Date actualEnd;
}
